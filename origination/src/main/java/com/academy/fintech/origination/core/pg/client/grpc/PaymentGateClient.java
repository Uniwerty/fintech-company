package com.academy.fintech.origination.core.pg.client.grpc;

import com.academy.fintech.pg.DisbursementRequest;
import com.academy.fintech.pg.DisbursementServiceGrpc;
import com.academy.fintech.pg.DisbursementServiceGrpc.DisbursementServiceBlockingStub;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class PaymentGateClient {
    private final DisbursementServiceBlockingStub stub;

    public PaymentGateClient(PaymentGateClientProperty property) {
        Channel channel = ManagedChannelBuilder
                .forAddress(property.host(), property.port())
                .usePlaintext()
                .build();
        stub = DisbursementServiceGrpc.newBlockingStub(channel);
    }

    public void makeDisbursement(DisbursementRequest request) {
        stub.makeDisbursement(request);
    }
}
