package com.academy.fintech.pg.public_interface.payment.dto;

import lombok.Builder;

import java.sql.Date;

@Builder
public record DisbursementResultDto(
        boolean isCompleted,
        Date disbursementDate
) {
}
