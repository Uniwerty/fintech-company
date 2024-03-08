package com.academy.fintech.origination.core.pg.client.grpc;

import com.academy.fintech.pg.PaymentGateServiceGrpc;
import com.academy.fintech.pg.PaymentGateServiceGrpc.PaymentGateServiceBlockingStub;
import com.academy.fintech.pg.PaymentRequest;
import com.academy.fintech.pg.PaymentResponse;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class PaymentGateClient {
    private final PaymentGateServiceBlockingStub stub;

    public PaymentGateClient(PaymentGateClientProperty property) {
        Channel channel = ManagedChannelBuilder.forAddress(property.host(), property.port()).usePlaintext().build();
        stub = PaymentGateServiceGrpc.newBlockingStub(channel);
    }

    public PaymentResponse makeDisbursement(PaymentRequest request) {
        return stub.makeDisbursement(request);
    }
}
