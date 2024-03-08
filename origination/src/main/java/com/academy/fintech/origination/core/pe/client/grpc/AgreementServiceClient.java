package com.academy.fintech.origination.core.pe.client.grpc;

import com.academy.fintech.agreement.AgreementActivationRequest;
import com.academy.fintech.agreement.AgreementActivationResponse;
import com.academy.fintech.agreement.AgreementCreationRequest;
import com.academy.fintech.agreement.AgreementCreationResponse;
import com.academy.fintech.agreement.AgreementServiceGrpc;
import com.academy.fintech.agreement.AgreementServiceGrpc.AgreementServiceBlockingStub;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class AgreementServiceClient {
    private final AgreementServiceBlockingStub stub;

    public AgreementServiceClient(ProductEngineClientProperty property) {
        Channel channel = ManagedChannelBuilder
                .forAddress(property.host(), property.port())
                .usePlaintext()
                .build();
        stub = AgreementServiceGrpc.newBlockingStub(channel);
    }

    public AgreementCreationResponse create(AgreementCreationRequest request) {
        return stub.create(request);
    }

    public AgreementActivationResponse activate(AgreementActivationRequest request) {
        return stub.activate(request);
    }
}
