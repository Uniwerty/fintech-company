package com.academy.fintech.origination.core.application.service;

import com.academy.fintech.origination.core.application.db.application.ApplicationService;
import com.academy.fintech.origination.core.application.mapper.DtoMapper;
import com.academy.fintech.origination.core.client.service.ClientCreationService;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCreationDto;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCreationResult;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationCreationService {
    private final ApplicationService applicationService;
    private final ClientCreationService clientCreationService;

    public ApplicationCreationResult create(ApplicationCreationDto applicationCreationDto) {
        UUID clientId = clientCreationService.createOrGetExisting(
                DtoMapper.mapApplicationCreationDtoToClientDto(applicationCreationDto)
        );
        ApplicationDto applicationDto =
                DtoMapper.mapApplicationCreationDtoToApplicationDto(clientId, applicationCreationDto);
        Optional<UUID> previousApplicationOptional = applicationService.findWithNewStatus(applicationDto);
        return previousApplicationOptional
                .map(ApplicationCreationResult::withFailure)
                .orElseGet(() ->
                        ApplicationCreationResult.withSuccess(applicationService.create(applicationDto))
                );
    }
}
