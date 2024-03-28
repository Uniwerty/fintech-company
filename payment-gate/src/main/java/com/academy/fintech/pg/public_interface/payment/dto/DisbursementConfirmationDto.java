package com.academy.fintech.pg.public_interface.payment.dto;

import lombok.Builder;

import java.sql.Date;
import java.util.UUID;

@Builder
public record DisbursementConfirmationDto(
        UUID agreementId,
        Date disbursementDate
) {
}
