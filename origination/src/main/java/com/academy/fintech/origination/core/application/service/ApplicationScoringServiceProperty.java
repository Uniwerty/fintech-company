package com.academy.fintech.origination.core.application.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "origination.application.service.scoring")
public record ApplicationScoringServiceProperty(
        String senderMail,
        String subject,
        String acceptedStatus,
        String closedStatus,
        String greeting,
        String message,
        String conclusion
) {
}
