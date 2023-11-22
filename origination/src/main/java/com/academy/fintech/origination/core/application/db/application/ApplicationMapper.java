package com.academy.fintech.origination.core.application.db.application;

import com.academy.fintech.origination.core.application.db.application.model.Application;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationDto;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public Application mapDtoToEntity(ApplicationDto applicationDto) {
        return Application.builder()
                .clientId(applicationDto.clientId())
                .requestedAmount(applicationDto.requiredAmount())
                .status(applicationDto.status())
                .creationDate(applicationDto.creationDate())
                .build();
    }

}
