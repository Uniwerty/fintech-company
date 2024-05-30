package com.academy.fintech.origination.core.application.service;

import com.academy.fintech.origination.core.application.db.application.ApplicationService;
import com.academy.fintech.origination.core.application.db.application.model.ApplicationStatus;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCancellationResult;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationCancellationService {
    private final ApplicationService applicationService;

    public ApplicationCancellationResult cancel(UUID id) {
        Optional<ApplicationDto> applicationOptional = applicationService.findById(id);
        if (applicationOptional.isEmpty()) {
            return ApplicationCancellationResult.withFailure("Requested application does not exist");
        }
        ApplicationStatus applicationStatus = applicationOptional.get().status();
        if (applicationStatus.equals(ApplicationStatus.NEW)
                || applicationStatus.equals(ApplicationStatus.SCORING)) {
            applicationService.deleteById(id);
        } else {
            return ApplicationCancellationResult.withFailure("Requested application is already processed");
        }
        return ApplicationCancellationResult.withSuccess();
    }
}
