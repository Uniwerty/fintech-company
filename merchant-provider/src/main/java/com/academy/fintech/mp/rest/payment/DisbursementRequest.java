package com.academy.fintech.mp.rest.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DisbursementRequest(
        @JsonProperty("agreement_id")
        String agreementId,
        @JsonProperty("client_id")
        String clientId,
        @JsonProperty("amount")
        String amount
) {
}
