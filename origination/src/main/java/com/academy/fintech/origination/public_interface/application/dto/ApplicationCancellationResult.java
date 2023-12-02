package com.academy.fintech.origination.public_interface.application.dto;

public record ApplicationCancellationResult(
        boolean isSuccess,
        String message
) {
    public static ApplicationCancellationResult withSuccess() {
        return new ApplicationCancellationResult(true, null);
    }

    public static ApplicationCancellationResult withFailure(String cause) {
        return new ApplicationCancellationResult(false, cause);
    }
}
