package com.academy.fintech.origination.core.application.mapper;

import com.academy.fintech.origination.core.application.status.ApplicationStatus;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCreationDto;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationDto;
import com.academy.fintech.origination.public_interface.client.dto.ClientDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DtoMapper {
    public ClientDto mapApplicationCreationDtoToClientDto(ApplicationCreationDto applicationCreationDto) {
        return ClientDto.builder()
                .firstName(applicationCreationDto.firstName())
                .lastName(applicationCreationDto.lastName())
                .email(applicationCreationDto.email())
                .salary(applicationCreationDto.salary())
                .build();
    }

    public ApplicationDto mapApplicationCreationDtoToApplicationDto(UUID clientId,
                                                                    ApplicationCreationDto applicationCreationDto) {
        return ApplicationDto.builder()
                .clientId(clientId)
                .status(ApplicationStatus.NEW)
                .requestedAmount(applicationCreationDto.requiredAmount())
                .creationDate(applicationCreationDto.creationDate())
                .build();
    }
}
