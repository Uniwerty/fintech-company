package com.academy.fintech.dwh.public_interface.agreement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Builder
public record AgreementDto(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("product_code")
        String productCode,
        @JsonProperty("client_id")
        UUID clientId,
        @JsonProperty("status")
        String status,
        @JsonProperty("term")
        int term,
        @JsonProperty("interest")
        BigDecimal interest,
        @JsonProperty("principal_amount")
        BigDecimal principalAmount,
        @JsonProperty("disbursement_amount")
        BigDecimal disbursementAmount,
        @JsonProperty("origination_amount")
        BigDecimal originationAmount,
        @JsonProperty("disbursement_date")
        Date disbursementDate,
        @JsonProperty("next_payment_date")
        Date nextPaymentDate
) {
}
