package com.academy.fintech.pg.core.mp.client.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DisbursementRequest(
        @JsonProperty("agreement_id")
        String agreementId,
        @JsonProperty("client_id")
        String clientId,
        @JsonProperty("amount")
        String amount
) {
}
