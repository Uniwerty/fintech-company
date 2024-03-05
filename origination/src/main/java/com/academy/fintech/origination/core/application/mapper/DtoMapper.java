package com.academy.fintech.origination.core.application.mapper;

import com.academy.fintech.origination.core.application.status.ApplicationStatus;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCreationDto;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationDto;
import com.academy.fintech.origination.public_interface.client.dto.ClientDto;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class DtoMapper {
    public static ClientDto mapApplicationCreationDtoToClientDto(ApplicationCreationDto applicationCreationDto) {
        return ClientDto.builder()
                .firstName(applicationCreationDto.firstName())
                .lastName(applicationCreationDto.lastName())
                .email(applicationCreationDto.email())
                .salary(applicationCreationDto.salary())
                .build();
    }

    public static ApplicationDto mapApplicationCreationDtoToApplicationDto(UUID clientId,
                                                                           ApplicationCreationDto applicationCreationDto) {
        return ApplicationDto.builder()
                .clientId(clientId)
                .status(ApplicationStatus.NEW)
                .requestedAmount(applicationCreationDto.requestedAmount())
                .creationDate(applicationCreationDto.creationDate())
                .build();
    }
}
