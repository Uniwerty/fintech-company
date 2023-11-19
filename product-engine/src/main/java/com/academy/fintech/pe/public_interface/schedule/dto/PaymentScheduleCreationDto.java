package com.academy.fintech.pe.public_interface.schedule.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Builder
public record PaymentScheduleCreationDto(
        UUID agreementId,
        BigDecimal principalAmount,
        BigDecimal interest,
        int term,
        Date disbursementDate
) {
}
