package com.academy.fintech.scoring.public_interface.application.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ApplicationDto(
        UUID clientId,
        BigDecimal salary,
        BigDecimal requestedAmount
) {
}
