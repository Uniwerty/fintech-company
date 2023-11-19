package com.academy.fintech.pe.public_interface.payment.dto;

import lombok.Builder;

import java.sql.Date;
import java.util.UUID;

@Builder
public record NextPaymentDto(
        UUID scheduleId,
        Date date
) {
}
