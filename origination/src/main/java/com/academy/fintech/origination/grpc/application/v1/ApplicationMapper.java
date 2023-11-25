package com.academy.fintech.origination.grpc.application.v1;

import com.academy.fintech.application.ApplicationCreationRequest;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCreationDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;

@Component
public class ApplicationMapper {
    public ApplicationCreationDto mapCreationRequestToDto(ApplicationCreationRequest request) {
        return ApplicationCreationDto.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .salary(new BigDecimal(request.getSalary()))
                .requestedAmount(new BigDecimal(request.getRequiredAmount()))
                .creationDate(new Date(request.getCreationDate()))
                .build();
    }
}
