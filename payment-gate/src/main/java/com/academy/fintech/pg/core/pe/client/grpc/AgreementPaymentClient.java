package com.academy.fintech.pg.core.pe.client.grpc;

import com.academy.fintech.pg.AgreementPaymentRequest;
import com.academy.fintech.pg.AgreementPaymentResponse;
import com.academy.fintech.pg.AgreementPaymentServiceGrpc;
import com.academy.fintech.pg.AgreementPaymentServiceGrpc.AgreementPaymentServiceBlockingStub;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class AgreementPaymentClient {
    private final AgreementPaymentServiceBlockingStub stub;

    public AgreementPaymentClient(ProductEngineClientProperty property) {
        Channel channel = ManagedChannelBuilder
                .forAddress(property.host(), property.port())
                .usePlaintext()
                .build();
        stub = AgreementPaymentServiceGrpc.newBlockingStub(channel);
    }

    public AgreementPaymentResponse pay(AgreementPaymentRequest request) {
        return stub.pay(request);
    }
}
