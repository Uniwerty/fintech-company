package com.academy.fintech.pg.core.mp.client.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DisbursementResultResponse(
        @JsonProperty("is_completed")
        boolean isCompleted,
        @JsonProperty("disbursement_date")
        long disbursementDate
) {
}
