package com.academy.fintech.pg.public_interface.payment.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record DisbursementCreationDto(
        UUID agreementId,
        UUID clientId,
        BigDecimal amount
) {
}
