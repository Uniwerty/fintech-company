package com.academy.fintech.mp.rest.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DisbursementResultRequest(
        @JsonProperty("agreement_id")
        String agreementId
) {
}
