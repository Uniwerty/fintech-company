package com.academy.fintech.pg.core.origination.client.grpc;

import com.academy.fintech.pg.ConfirmationRequest;
import com.academy.fintech.pg.DisbursementServiceGrpc;
import com.academy.fintech.pg.DisbursementServiceGrpc.DisbursementServiceBlockingStub;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class OriginationClient {
    private final DisbursementServiceBlockingStub stub;

    public OriginationClient(OriginationClientProperty property) {
        Channel channel = ManagedChannelBuilder
                .forAddress(property.host(), property.port())
                .usePlaintext()
                .build();
        stub = DisbursementServiceGrpc.newBlockingStub(channel);
    }

    public void confirmDisbursement(ConfirmationRequest request) {
        stub.confirmDisbursement(request);
    }
}
