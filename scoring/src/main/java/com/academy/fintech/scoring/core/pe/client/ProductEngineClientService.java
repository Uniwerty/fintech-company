package com.academy.fintech.scoring.core.pe.client;

import com.academy.fintech.scoring.ApplicationCalculationRequest;
import com.academy.fintech.scoring.ApplicationCalculationResponse;
import com.academy.fintech.scoring.core.pe.client.grpc.ProductEngineGrpcClient;
import com.academy.fintech.scoring.public_interface.application.dto.ApplicationCalculationResult;
import com.academy.fintech.scoring.public_interface.application.dto.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return ProductEngineClientMapper.mapResponseToResult(response);
    }
}
