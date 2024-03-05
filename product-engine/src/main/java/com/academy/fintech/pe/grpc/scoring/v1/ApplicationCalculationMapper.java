package com.academy.fintech.pe.grpc.scoring.v1;

import com.academy.fintech.pe.public_interface.application.dto.ApplicationDto;
import com.academy.fintech.scoring.ApplicationCalculationRequest;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class ApplicationCalculationMapper {
    public static ApplicationDto mapRequestToDto(ApplicationCalculationRequest request) {
        return ApplicationDto.builder()
                .clientId(UUID.fromString(request.getClientId()))
                .salary(new BigDecimal(request.getSalary()))
                .requestedAmount(new BigDecimal(request.getRequestedAmount()))
                .build();
    }
}
