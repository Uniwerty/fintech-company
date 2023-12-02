package com.academy.fintech.api.core.origination.client;

import com.academy.fintech.api.public_interface.application.dto.ApplicationDto;
import com.academy.fintech.application.ApplicationCreationRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OriginationClientMapper {
    public static ApplicationCreationRequest mapDtoToCreationRequest(ApplicationDto applicationDto) {
        return ApplicationCreationRequest.newBuilder()
                .setFirstName(applicationDto.firstName())
                .setLastName(applicationDto.lastName())
                .setEmail(applicationDto.email())
                .setSalary(applicationDto.salary())
                .setDisbursementAmount(applicationDto.amount())
                .build();
    }
}
