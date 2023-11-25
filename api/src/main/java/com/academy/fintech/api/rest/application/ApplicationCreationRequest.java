package com.academy.fintech.api.rest.application;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApplicationCreationRequest(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("email")
        String email,
        @JsonProperty("salary")
        int salary,
        @JsonProperty("amount")
        int amount
) {
}
