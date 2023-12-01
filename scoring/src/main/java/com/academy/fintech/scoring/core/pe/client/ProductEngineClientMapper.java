package com.academy.fintech.scoring.core.pe.client;

import com.academy.fintech.scoring.ApplicationCalculationResponse;
import com.academy.fintech.scoring.public_interface.application.dto.ApplicationCalculationResult;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class ProductEngineClientMapper {
    public static ApplicationCalculationResult mapResponseToResult(ApplicationCalculationResponse response) {
        return ApplicationCalculationResult.builder()
                .clientId(UUID.fromString(response.getClientId()))
                .periodPayment(new BigDecimal(response.getPeriodPayment()))
                .daysOverdue(response.getDaysOverdue())
                .build();
    }
}
