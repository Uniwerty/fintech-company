package com.academy.fintech.origination.public_interface.application.dto;

import com.academy.fintech.origination.core.application.status.ApplicationStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Builder
public record ApplicationDto(
        UUID clientId,
        ApplicationStatus status,
        BigDecimal requestedAmount,
        Date creationDate
) {
}
