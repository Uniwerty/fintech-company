package com.academy.fintech.mp.rest.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DisbursementResponse(
        @JsonProperty("disbursement_date")
        long disbursementDate
) {
}
