package com.academy.fintech.origination.public_interface.agreement.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record AgreementCreationDto(
        UUID clientId,
        String productCode,
        BigDecimal disbursementAmount,
        BigDecimal originationAmount,
        BigDecimal interest,
        int term
) {
}
