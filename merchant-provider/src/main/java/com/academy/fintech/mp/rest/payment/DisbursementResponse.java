package com.academy.fintech.mp.rest.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DisbursementResponse(
        @JsonProperty("is_completed")
        boolean isCompleted,
        @JsonProperty("date")
        long date
) {
}
