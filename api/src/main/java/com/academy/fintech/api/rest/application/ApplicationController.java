package com.academy.fintech.api.rest.application;

import com.academy.fintech.api.core.application.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/create")
    public String create(@RequestBody ApplicationCreationRequest applicationCreationRequest) {
        log.info("Got creation request: {}", applicationCreationRequest);
        return applicationService.create(
                ApplicationMapper.mapCreationRequestToDto(applicationCreationRequest)
        );
    }

    @PostMapping("/cancel")
    public String cancel(@RequestBody ApplicationCancellationRequest applicationCancellationRequest) {
        log.info("Got cancellation request: {}", applicationCancellationRequest);
        return applicationService.cancel(applicationCancellationRequest.applicationId());
    }
}
