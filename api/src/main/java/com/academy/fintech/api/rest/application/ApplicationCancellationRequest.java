package com.academy.fintech.api.rest.application;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApplicationCancellationRequest(
        @JsonProperty("application_id")
        String applicationId
) {
}
