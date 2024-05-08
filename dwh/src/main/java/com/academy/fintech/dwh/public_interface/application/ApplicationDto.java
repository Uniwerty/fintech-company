package com.academy.fintech.dwh.public_interface.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Builder
public record ApplicationDto(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("client_id")
        UUID clientId,
        @JsonProperty("requested_amount")
        BigDecimal requestedAmount,
        @JsonProperty("status")
        String status,
        @JsonProperty("creation_date")
        Date creationDate,
        @JsonProperty("last_update_date")
        Date lastUpdateDate
) {
}
