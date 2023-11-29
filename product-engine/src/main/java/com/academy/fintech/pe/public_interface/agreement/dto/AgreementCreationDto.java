package com.academy.fintech.pe.public_interface.agreement.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record AgreementCreationDto(
        String productCode,
        UUID clientId,
        int term,
        BigDecimal interest,
        BigDecimal principalAmount,
        BigDecimal disbursementAmount,
        BigDecimal originationAmount
) {
}
