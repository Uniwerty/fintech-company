package com.academy.fintech.origination.public_interface.application.dto;

import java.util.UUID;

public record ApplicationCreationResult(
        boolean isSuccess,
        UUID applicationId
) {
    public static ApplicationCreationResult withSuccess(UUID applicationId) {
        return new ApplicationCreationResult(true, applicationId);
    }

    public static ApplicationCreationResult withFailure(UUID applicationId) {
        return new ApplicationCreationResult(false, applicationId);
    }
}
