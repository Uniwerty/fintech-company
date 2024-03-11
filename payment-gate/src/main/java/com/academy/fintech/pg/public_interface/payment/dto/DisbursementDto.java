package com.academy.fintech.pg.public_interface.payment.dto;

import lombok.Builder;

@Builder
public record DisbursementDto(
        String clientId,
        String amount
) {
}
