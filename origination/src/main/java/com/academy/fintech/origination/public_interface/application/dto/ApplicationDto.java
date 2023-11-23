package com.academy.fintech.origination.public_interface.application.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Builder
public record ApplicationDto(
        UUID clientId,
        BigDecimal requiredAmount,
        Date creationDate
) {
}
