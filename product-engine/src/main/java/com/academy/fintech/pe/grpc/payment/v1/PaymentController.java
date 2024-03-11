package com.academy.fintech.pe.grpc.payment.v1;

import com.academy.fintech.pe.core.agreement.service.AgreementPaymentService;
import com.academy.fintech.pg.AgreementPaymentRequest;
import com.academy.fintech.pg.AgreementPaymentResponse;
import com.academy.fintech.pg.AgreementPaymentServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
@RequiredArgsConstructor
public class PaymentController extends AgreementPaymentServiceGrpc.AgreementPaymentServiceImplBase {
    private final AgreementPaymentService agreementPaymentService;

    @Override
    public void pay(AgreementPaymentRequest request,
                    StreamObserver<AgreementPaymentResponse> responseObserver) {
        boolean isCompleted = agreementPaymentService.pay(PaymentMapper.mapRequestToDto(request));
        responseObserver.onNext(
                AgreementPaymentResponse.newBuilder()
                        .setIsCompleted(isCompleted)
                        .build()
        );
        responseObserver.onCompleted();
    }
}
