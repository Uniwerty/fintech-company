package com.academy.fintech.mp.public_interface.dto.disbursement;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record DisbursementCreationDto(
        UUID agreementId,
        UUID clientId,
        BigDecimal amount
) {
}
