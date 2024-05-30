package com.academy.fintech.origination.core.application.db.application;

import com.academy.fintech.origination.core.application.db.application.model.Application;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationDto;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationScoringDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationMapper {

    public static Application mapDtoToEntity(ApplicationDto applicationDto) {
        return Application.builder()
                .clientId(applicationDto.clientId())
                .requestedAmount(applicationDto.requestedAmount())
                .status(applicationDto.status())
                .creationDate(applicationDto.creationDate())
                .lastUpdateDate(applicationDto.lastUpdateDate())
                .build();
    }

    public static ApplicationDto mapEntityToDto(Application application) {
        return ApplicationDto.builder()
                .clientId(application.getClientId())
                .status(application.getStatus())
                .requestedAmount(application.getRequestedAmount())
                .creationDate(application.getCreationDate())
                .lastUpdateDate(application.getLastUpdateDate())
                .build();
    }

    public static ApplicationScoringDto mapEntityToScoringDto(Application application) {
        return ApplicationScoringDto.builder()
                .id(application.getId())
                .clientId(application.getClientId())
                .requestedAmount(application.getRequestedAmount())
                .build();
    }
}
