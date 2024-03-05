package com.academy.fintech.origination.public_interface.application.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ScoringRequestDto(
        UUID clientId,
        BigDecimal salary,
        BigDecimal requestedAmount
) {
}
