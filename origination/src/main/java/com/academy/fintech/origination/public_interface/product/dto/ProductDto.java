package com.academy.fintech.origination.public_interface.product.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDto(
        BigDecimal originationAmount,
        BigDecimal interest,
        int term
) {
}
