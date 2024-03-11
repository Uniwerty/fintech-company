package com.academy.fintech.pg.core.mp.client.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DisbursementResponse(
        @JsonProperty("disbursement_date")
        long disbursementDate
) {
}
