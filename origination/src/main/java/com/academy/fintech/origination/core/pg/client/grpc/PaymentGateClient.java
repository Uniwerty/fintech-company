package com.academy.fintech.origination.core.pg.client.grpc;

import com.academy.fintech.pg.DisbursementRequest;
import com.academy.fintech.pg.DisbursementResponse;
import com.academy.fintech.pg.DisbursementServiceGrpc;
import com.academy.fintech.pg.DisbursementServiceGrpc.DisbursementServiceStub;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

@Component
public class PaymentGateClient {
    private final DisbursementServiceStub stub;

    public PaymentGateClient(PaymentGateClientProperty property) {
        Channel channel = ManagedChannelBuilder.forAddress(property.host(), property.port()).usePlaintext().build();
        stub = DisbursementServiceGrpc.newStub(channel);
    }

    public void makeDisbursement(DisbursementRequest request,
                                 StreamObserver<DisbursementResponse> responseObserver) {
        stub.makeDisbursement(request, responseObserver);
    }
}
