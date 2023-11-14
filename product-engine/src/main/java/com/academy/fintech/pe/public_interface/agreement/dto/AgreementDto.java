package com.academy.fintech.pe.public_interface.agreement.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Date;

@Builder
public record AgreementDto (
    String id,
    String productCode,
    String clientId,
    int loanTerm,
    BigDecimal interest,
    BigDecimal disbursementAmount,
    BigDecimal originationAmount,
    Date disbursementDate
) {
}
