package com.academy.fintech.mp.rest.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DisbursementResultResponse(
        @JsonProperty("is_completed")
        boolean isCompleted,
        @JsonProperty("disbursement_date")
        long disbursementDate
) {
}
