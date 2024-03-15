package com.academy.fintech.pe.public_interface.payment.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record PeriodPaymentDto(
        UUID id,
        UUID scheduleId,
        BigDecimal amount,
        int periodNumber
) {
}
