package com.academy.fintech.dwh.core.application.db;

import com.academy.fintech.dwh.core.application.db.model.Application;
import com.academy.fintech.dwh.public_interface.application.ApplicationDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationMapper {
    public static Application mapDtoToEntity(ApplicationDto dto) {
        return Application.builder()
                .id(dto.id())
                .clientId(dto.clientId())
                .requestedAmount(dto.requestedAmount())
                .status(dto.status())
                .creationDate(dto.creationDate())
                .lastUpdateDate(dto.lastUpdateDate())
                .build();
    }
}
