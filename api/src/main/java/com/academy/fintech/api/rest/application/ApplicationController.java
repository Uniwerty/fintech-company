package com.academy.fintech.api.rest.application;

import com.academy.fintech.api.core.application.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;

    @PostMapping("/create")
    public String create(@RequestBody ApplicationCreationRequest applicationCreationRequest) {
        return applicationService.create(
                applicationMapper.mapCreationRequestToDto(applicationCreationRequest)
        );
    }

    @PostMapping("/cancel")
    public String cancel(@RequestBody ApplicationCancellationRequest applicationCancellationRequest) {
        return applicationService.cancel(applicationCancellationRequest.applicationId());
    }
}
