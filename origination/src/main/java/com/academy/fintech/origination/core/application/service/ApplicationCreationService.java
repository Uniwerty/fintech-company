package com.academy.fintech.origination.core.application.service;

import com.academy.fintech.origination.core.application.db.application.ApplicationService;
import com.academy.fintech.origination.core.client.service.ClientCreationService;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCreationDto;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationDto;
import com.academy.fintech.origination.public_interface.client.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationCreationService {
    private final ApplicationService applicationService;
    private final ClientCreationService clientCreationService;

    public UUID create(ApplicationCreationDto applicationCreationDto) {
        UUID clientId = clientCreationService.createOrGetExisting(
                ClientDto.builder()
                        .firstName(applicationCreationDto.firstName())
                        .lastName(applicationCreationDto.lastName())
                        .email(applicationCreationDto.email())
                        .salary(applicationCreationDto.salary())
                        .build()
        );
        return applicationService.create(
                ApplicationDto.builder()
                        .clientId(clientId)
                        .requiredAmount(applicationCreationDto.requiredAmount())
                        .creationDate(applicationCreationDto.creationDate())
                        .build()
        );
    }
}
