package com.academy.fintech.origination.core.application.db.application;

import com.academy.fintech.origination.core.application.db.application.model.Application;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationDto;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public Application mapDtoToEntity(ApplicationDto applicationDto) {
        return Application.builder()
                .clientId(applicationDto.clientId())
                .requestedAmount(applicationDto.requestedAmount())
                .status(applicationDto.status())
                .creationDate(applicationDto.creationDate())
                .build();
    }

    public ApplicationDto mapEntityToDto(Application application) {
        return ApplicationDto.builder()
                .clientId(application.getClientId())
                .status(application.getStatus())
                .requestedAmount(application.getRequestedAmount())
                .creationDate(application.getCreationDate())
                .build();
    }
}
