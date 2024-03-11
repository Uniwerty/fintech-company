package com.academy.fintech.origination.core.pg.client;

import com.academy.fintech.origination.core.pg.client.grpc.PaymentGateClient;
import com.academy.fintech.origination.public_interface.payment.dto.DisbursementRequestDto;
import com.academy.fintech.pg.DisbursementResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentGateClientService {
    private final PaymentGateClient paymentGateClient;

    public void makeDisbursement(DisbursementRequestDto requestDto,
                                 StreamObserver<DisbursementResponse> responseObserver) {
        paymentGateClient.makeDisbursement(
                PaymentGateClientMapper.mapDtoToRequest(requestDto),
                responseObserver
        );
    }
}
