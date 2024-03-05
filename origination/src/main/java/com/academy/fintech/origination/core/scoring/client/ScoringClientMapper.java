package com.academy.fintech.origination.core.scoring.client;

import com.academy.fintech.origination.public_interface.application.dto.ScoringRequestDto;
import com.academy.fintech.scoring.ScoringRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ScoringClientMapper {
    public static ScoringRequest mapDtoToRequest(ScoringRequestDto requestDto) {
        return ScoringRequest.newBuilder()
                .setClientId(requestDto.clientId().toString())
                .setSalary(requestDto.salary().toString())
                .setRequestedAmount(requestDto.requestedAmount().toString())
                .build();
    }
}
