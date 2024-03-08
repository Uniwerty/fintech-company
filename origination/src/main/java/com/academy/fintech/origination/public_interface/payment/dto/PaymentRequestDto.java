package com.academy.fintech.origination.public_interface.payment.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record PaymentRequestDto(
        UUID clientId,
        BigDecimal amount
) {
}
