package com.academy.fintech.pe.public_interface.payment.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Builder
public record PaymentCreationDto(
        UUID scheduleId,
        BigDecimal principalAmount,
        BigDecimal interest,
        int term,
        Date disbursementDate
) {
}
