package com.academy.fintech.pe.public_interface.agreement.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Builder
public record AgreementDto (
    UUID id,
    String productCode,
    UUID clientId,
    int term,
    BigDecimal interest,
    BigDecimal disbursementAmount,
    BigDecimal originationAmount,
    Date disbursementDate
) {
}
