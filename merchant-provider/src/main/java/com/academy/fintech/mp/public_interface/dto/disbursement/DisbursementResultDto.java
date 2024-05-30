package com.academy.fintech.mp.public_interface.dto.disbursement;

import lombok.Builder;

import java.sql.Date;

@Builder
public record DisbursementResultDto(
        boolean isCompleted,
        Date date
) {
}
