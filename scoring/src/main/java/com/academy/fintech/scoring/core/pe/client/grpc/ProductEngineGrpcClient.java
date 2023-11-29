package com.academy.fintech.scoring.core.pe.client.grpc;

import com.academy.fintech.scoring.ApplicationCalculationRequest;
import com.academy.fintech.scoring.ApplicationCalculationResponse;
import com.academy.fintech.scoring.ApplicationCalculationServiceGrpc;
import com.academy.fintech.scoring.ApplicationCalculationServiceGrpc.ApplicationCalculationServiceBlockingStub;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProductEngineGrpcClient {
    private final ApplicationCalculationServiceBlockingStub stub;

    public ProductEngineGrpcClient(ProductEngineGrpcClientProperty property) {
        Channel channel = ManagedChannelBuilder.forAddress(property.host(), property.port()).usePlaintext().build();
        this.stub = ApplicationCalculationServiceGrpc.newBlockingStub(channel);
    }

    public ApplicationCalculationResponse getApplicationInfo(ApplicationCalculationRequest request) {
        return stub.getApplicationInfo(request);
    }
}
