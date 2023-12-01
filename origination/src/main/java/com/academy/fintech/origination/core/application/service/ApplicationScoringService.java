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
    private static final int SCORING_DELAY = 5;
    private static final String SENDER_MAIL = "noreply@fintech.academy.com";
    private final ScoringClientService scoringClientService;
    private final ApplicationService applicationService;
    private final ClientService clientService;
    private final JavaMailSender javaMailSender;

    @Scheduled(fixedDelay = SCORING_DELAY, timeUnit = TimeUnit.MINUTES)
    public void scoreNewApplications() {
        log.info("Sending new applications to scoring");
        for (ApplicationScoringDto applicationScoringDto : applicationService.findAllNew()) {
            updateScoring(applicationScoringDto.id());
            ClientDto clientDto = clientService.findById(applicationScoringDto.clientId()).orElseThrow();
            boolean approved = scoringClientService.getApprovalVerdict(
                    ScoringRequestDto.builder()
                            .clientId(applicationScoringDto.clientId())
                            .salary(clientDto.salary())
                            .requestedAmount(applicationScoringDto.requestedAmount())
                            .build()
            );
            if (approved) {
                updateAccepted(applicationScoringDto.id());
                sendEmail(clientDto, applicationScoringDto.id(), "одобрена");
            } else {
                updateClosed(applicationScoringDto.id());
                sendEmail(clientDto, applicationScoringDto.id(), "отклонена");
            }
        }
    }

    private void sendEmail(ClientDto clientDto, UUID applicationId, String verdict) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER_MAIL);
        message.setTo(clientDto.email());
        message.setSubject("Статус вашей заявки обновлен");
        message.setText(
                String.format(
                        "Уважаемый(ая) %s %s!%nСообщаем Вам, что заявка № %s %s.%nFintech Academy",
                        clientDto.lastName(),
                        clientDto.firstName(),
                        applicationId.toString(),
                        verdict
                )
        );
        javaMailSender.send(message);
    }

    private void updateScoring(UUID id) {
        applicationService.updateStatus(id, ApplicationStatus.SCORING);
    }

    private void updateAccepted(UUID id) {
        applicationService.updateStatus(id, ApplicationStatus.ACCEPTED);
    }

    private void updateClosed(UUID id) {
        applicationService.updateStatus(id, ApplicationStatus.CLOSED);
    }
}
