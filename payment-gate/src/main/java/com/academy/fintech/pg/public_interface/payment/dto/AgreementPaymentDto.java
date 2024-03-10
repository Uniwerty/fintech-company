package com.academy.fintech.pg.public_interface.payment.dto;

import lombok.Builder;

@Builder
public record AgreementPaymentDto(
        String agreementId,
        String amount
) {
}
