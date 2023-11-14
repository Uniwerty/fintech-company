package com.academy.fintech.pe.grpc.agreement.v1;

import com.academy.fintech.agreement.AgreementActivationRequest;
import com.academy.fintech.agreement.AgreementActivationResponse;
import com.academy.fintech.agreement.AgreementCreationRequest;
import com.academy.fintech.agreement.AgreementCreationResponse;
import com.academy.fintech.agreement.AgreementServiceGrpc;
import com.academy.fintech.pe.core.agreement.service.AgreementActivationService;
import com.academy.fintech.pe.core.agreement.service.AgreementCreationService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
@RequiredArgsConstructor
public class AgreementController extends AgreementServiceGrpc.AgreementServiceImplBase {
    private final AgreementMapper agreementMapper;
    private final AgreementCreationService agreementCreationService;
    private final AgreementActivationService agreementActivationService;

    @Override
    public void create(AgreementCreationRequest request,
                       StreamObserver<AgreementCreationResponse> responseObserver) {
        String createdAgreementId =
                agreementCreationService.create(agreementMapper.mapCreationRequestToDto(request));
        responseObserver.onNext(
                AgreementCreationResponse.newBuilder()
                        .setAgreementId(createdAgreementId)
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void activate(AgreementActivationRequest request,
                         StreamObserver<AgreementActivationResponse> responseObserver) {
        super.activate(request, responseObserver);
    }
}
