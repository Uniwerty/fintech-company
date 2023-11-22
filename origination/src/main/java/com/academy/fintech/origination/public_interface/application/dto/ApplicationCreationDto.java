package com.academy.fintech.origination.public_interface.application.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Date;

@Builder
public record ApplicationCreationDto(
        String firstName,
        String lastName,
        String email,
        BigDecimal salary,
        BigDecimal requiredAmount,
        Date creationDate
) {
}
