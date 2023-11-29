package com.academy.fintech.scoring.core.pe.client;

import com.academy.fintech.scoring.ApplicationCalculationRequest;
import com.academy.fintech.scoring.ApplicationCalculationResponse;
import com.academy.fintech.scoring.core.pe.client.grpc.ProductEngineGrpcClient;
import com.academy.fintech.scoring.public_interface.application.dto.ApplicationCalculationResult;
import com.academy.fintech.scoring.public_interface.application.dto.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductEngineClientService {
    private final ProductEngineGrpcClient productEngineGrpcClient;

    public ApplicationCalculationResult getApplicationInfo(ApplicationDto applicationDto) {
        ApplicationCalculationResponse response = productEngineGrpcClient.getApplicationInfo(
                ApplicationCalculationRequest.newBuilder()
                        .setClientId(applicationDto.clientId().toString())
                        .setSalary(applicationDto.salary().toString())
                        .setRequestedAmount(applicationDto.requestedAmount().toString())
                        .build()
        );
        return mapResponseToResult(response);
    }

    private static ApplicationCalculationResult mapResponseToResult(ApplicationCalculationResponse response) {
        return ApplicationCalculationResult.builder()
                .clientId(UUID.fromString(response.getClientId()))
                .periodPayment(new BigDecimal(response.getPeriodPayment()))
                .daysOverdue(response.getDaysOverdue())
                .build();
    }
}
