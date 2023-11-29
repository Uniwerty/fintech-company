package com.academy.fintech.pe.public_interface.payment.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Builder
public record PaymentDto(
        UUID id,
        UUID scheduleId,
        String status,
        Date date,
        BigDecimal interestPayment,
        BigDecimal principalPayment,
        int periodNumber
) {
}
