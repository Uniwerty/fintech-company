package com.academy.fintech.api.rest.application;

import com.academy.fintech.api.public_interface.application.dto.ApplicationDto;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

@UtilityClass
public class ApplicationMapper {

    public static ApplicationDto mapCreationRequestToDto(ApplicationCreationRequest request) {
        return ApplicationDto.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .amount(request.amount())
                .salary(request.salary())
                .build();
    }

}
