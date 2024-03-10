package com.academy.fintech.pg.rest.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentRequest(
        @JsonProperty("agreement_id")
        String agreementId,
        @JsonProperty("amount")
        String amount
) {
}
