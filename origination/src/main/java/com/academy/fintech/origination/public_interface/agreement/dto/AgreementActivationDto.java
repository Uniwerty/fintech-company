package com.academy.fintech.origination.public_interface.agreement.dto;

import lombok.Builder;

import java.sql.Date;
import java.util.UUID;

@Builder
public record AgreementActivationDto(
        UUID id,
        Date disbursementDate
) {
}
