package com.academy.fintech.pg.core.mp.client.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DisbursementResultRequest(
        @JsonProperty("agreement_id")
        UUID agreementId
) {
}
