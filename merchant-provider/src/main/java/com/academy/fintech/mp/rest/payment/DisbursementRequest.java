package com.academy.fintech.mp.rest.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DisbursementRequest(
        @JsonProperty("client_id")
        String clientId,
        @JsonProperty("amount")
        String amount
) {
}
