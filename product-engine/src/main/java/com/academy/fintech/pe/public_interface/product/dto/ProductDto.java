package com.academy.fintech.pe.public_interface.product.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDto(
       BigDecimal maxOriginationAmount,
       BigDecimal maxInterest,
       int maxTerm
) {
}
