package com.academy.fintech.scoring.grpc.scoring.v1;

import com.academy.fintech.scoring.ScoringRequest;
import com.academy.fintech.scoring.public_interface.dto.ScoringRequestDto;
import com.academy.fintech.scoring.public_interface.application.dto.ApplicationDto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class ScoringMapper {
    public static ApplicationDto mapRequestToDto(ScoringRequest request) {
        return ApplicationDto.builder()
                .clientId(UUID.fromString(request.getClientId()))
                .salary(new BigDecimal(request.getSalary()))
                .requestedAmount(new BigDecimal(request.getRequestedAmount()))
                .build();
    }
}
