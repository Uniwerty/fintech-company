package com.academy.fintech.origination.core.application.mapper;

import com.academy.fintech.origination.core.application.status.ApplicationStatus;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCreationDto;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationDto;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationScoringDto;
import com.academy.fintech.origination.public_interface.client.dto.ClientDto;
import com.academy.fintech.origination.public_interface.payment.dto.DisbursementRequestDto;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class ApplicationDtoMapper {
    public static ClientDto mapCreationDtoToClientDto(ApplicationCreationDto creationDto) {
        return ClientDto.builder()
                .firstName(creationDto.firstName())
                .lastName(creationDto.lastName())
                .email(creationDto.email())
                .salary(creationDto.salary())
                .build();
    }

    public static ApplicationDto mapCreationDtoToApplicationDto(UUID clientId,
                                                                ApplicationCreationDto creationDto) {
        return ApplicationDto.builder()
                .clientId(clientId)
                .status(ApplicationStatus.NEW)
                .requestedAmount(creationDto.requestedAmount())
                .creationDate(creationDto.creationDate())
                .build();
    }

    public static DisbursementRequestDto mapScoringDtoToDisbursementRequestDto(ApplicationScoringDto scoringDto) {
        return DisbursementRequestDto.builder()
                .clientId(scoringDto.clientId())
                .amount(scoringDto.requestedAmount())
                .build();
    }
}
