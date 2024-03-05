package com.academy.fintech.origination.core.application.service;

import com.academy.fintech.origination.core.application.db.application.ApplicationService;
import com.academy.fintech.origination.core.application.status.ApplicationStatus;
import com.academy.fintech.origination.core.client.db.client.ClientService;
import com.academy.fintech.origination.core.scoring.client.ScoringClientService;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationScoringDto;
import com.academy.fintech.origination.public_interface.application.dto.ScoringRequestDto;
import com.academy.fintech.origination.public_interface.client.dto.ClientDto;
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
    private final ScoringClientService scoringClientService;
    private final ApplicationService applicationService;
    private final ClientService clientService;
    private final JavaMailSender javaMailSender;
    private final ApplicationScoringServiceProperty property;

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void scoreNewApplications() {
        log.info("Sending new applications to scoring");
        for (ApplicationScoringDto applicationScoringDto : applicationService.findAllWithNewStatus()) {
            applicationService.updateStatus(applicationScoringDto.id(), ApplicationStatus.SCORING);
            ClientDto clientDto = clientService.findById(applicationScoringDto.clientId()).orElseThrow();
            boolean isApproved = scoringClientService.getApprovalVerdict(
                    ScoringRequestDto.builder()
                            .clientId(applicationScoringDto.clientId())
                            .salary(clientDto.salary())
                            .requestedAmount(applicationScoringDto.requestedAmount())
                            .build()
            );
            if (isApproved) {
                applicationService.updateStatus(applicationScoringDto.id(), ApplicationStatus.ACCEPTED);
                sendEmail(clientDto, applicationScoringDto.id(), property.acceptedStatus());
            } else {
                applicationService.updateStatus(applicationScoringDto.id(), ApplicationStatus.CLOSED);
                sendEmail(clientDto, applicationScoringDto.id(), property.closedStatus());
            }
        }
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
}
