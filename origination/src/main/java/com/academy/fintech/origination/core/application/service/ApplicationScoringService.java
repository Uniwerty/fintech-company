package com.academy.fintech.origination.core.application.service;

import com.academy.fintech.origination.core.application.db.application.ApplicationService;
import com.academy.fintech.origination.core.application.service.configuration.ApplicationScoringServiceProperty;
import com.academy.fintech.origination.core.application.db.application.model.ApplicationStatus;
import com.academy.fintech.origination.core.client.db.client.ClientService;
import com.academy.fintech.origination.core.pe.client.ProductEngineClientService;
import com.academy.fintech.origination.core.pg.client.PaymentGateClientService;
import com.academy.fintech.origination.core.scoring.client.ScoringClientService;
import com.academy.fintech.origination.public_interface.agreement.dto.AgreementCreationDto;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationScoringDto;
import com.academy.fintech.origination.public_interface.application.dto.ScoringRequestDto;
import com.academy.fintech.origination.public_interface.client.dto.ClientDto;
import com.academy.fintech.origination.public_interface.payment.dto.DisbursementRequestDto;
import com.academy.fintech.origination.public_interface.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationScoringService {
    private final ProductEngineClientService productEngineClientService;
    private final ScoringClientService scoringClientService;
    private final PaymentGateClientService paymentGateClientService;
    private final ApplicationService applicationService;
    private final ClientService clientService;
    private final JavaMailSender javaMailSender;
    private final ApplicationScoringServiceProperty property;

    /**
     * Sends new applications to scoring, changes their status
     * and sends emails to clients about status change.
     * <p>
     * If the application is approved, processes it.
     */
    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void scoreNewApplications() {
        log.info("Sending new applications to scoring");
        for (ApplicationScoringDto applicationScoringDto : applicationService.findAllWithNewStatus()) {
            UUID applicationId = applicationScoringDto.id();
            UUID clientId = applicationScoringDto.clientId();
            applicationService.updateStatus(applicationId, ApplicationStatus.SCORING);
            logApplicationStatusChange(applicationId, ApplicationStatus.SCORING);
            ClientDto clientDto = clientService.findById(clientId).orElseThrow();
            boolean isApproved = scoringClientService.getApprovalVerdict(
                    ScoringRequestDto.builder()
                            .clientId(clientId)
                            .salary(clientDto.salary())
                            .requestedAmount(applicationScoringDto.requestedAmount())
                            .build()
            );
            if (isApproved) {
                applicationService.updateStatus(applicationId, ApplicationStatus.ACCEPTED);
                logApplicationStatusChange(applicationId, ApplicationStatus.ACCEPTED);
                sendEmail(clientDto, applicationId, property.acceptedStatus());
                processAcceptedApplication(applicationScoringDto);
            } else {
                applicationService.updateStatus(applicationId, ApplicationStatus.CLOSED);
                logApplicationStatusChange(applicationId, ApplicationStatus.CLOSED);
                sendEmail(clientDto, applicationId, property.closedStatus());
            }
        }
    }

    /**
     * Creates an agreement according to the given application,
     * makes a disbursement asynchronously and activates the agreement.
     *
     * @param applicationScoringDto the DTO to process the application
     */
    private void processAcceptedApplication(ApplicationScoringDto applicationScoringDto) {
        ProductDto productDto = productEngineClientService
                .getProductParameters(property.defaultProductCode());
        UUID agreementId = productEngineClientService.createAgreement(
                AgreementCreationDto.builder()
                        .clientId(applicationScoringDto.clientId())
                        .productCode(property.defaultProductCode())
                        .disbursementAmount(applicationScoringDto.requestedAmount())
                        .originationAmount(productDto.originationAmount())
                        .interest(productDto.interest())
                        .term(productDto.term())
                        .build()
        );
        log.info("The agreement {} is created", agreementId);
        paymentGateClientService.makeDisbursement(
                DisbursementRequestDto.builder()
                        .agreementId(agreementId)
                        .clientId(applicationScoringDto.clientId())
                        .amount(applicationScoringDto.requestedAmount())
                        .build()
        );
    }

    private void sendEmail(ClientDto clientDto, UUID applicationId, String verdict) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(property.senderMail());
        message.setTo(clientDto.email());
        message.setSubject(property.subject());
        message.setText(
                getEmailMessage(
                        clientDto.lastName(),
                        clientDto.firstName(),
                        applicationId,
                        verdict
                )
        );
        javaMailSender.send(message);
    }

    private String getEmailMessage(String lastName,
                                   String firstName,
                                   UUID applicationId,
                                   String verdict) {
        return String.format(
                "%s %s %s!%n%s %s %s.%n%s",
                property.greeting(),
                lastName,
                firstName,
                property.message(),
                applicationId.toString(),
                verdict,
                property.conclusion()
        );
    }

    private static void logApplicationStatusChange(UUID applicationId, ApplicationStatus newStatus) {
        log.info("Application {} status updated to {}", applicationId, newStatus);
    }
}
