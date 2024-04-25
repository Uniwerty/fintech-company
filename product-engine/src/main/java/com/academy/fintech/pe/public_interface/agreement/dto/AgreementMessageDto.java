package com.academy.fintech.pe.public_interface.agreement.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AgreementMessageDto(
        UUID id,
        String payload
) {
}
