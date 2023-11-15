package com.academy.fintech.pe.grpc.agreement.v1;

import com.academy.fintech.agreement.AgreementActivationRequest;
import com.academy.fintech.agreement.AgreementActivationResponse;
import com.academy.fintech.agreement.AgreementCreationRequest;
import com.academy.fintech.agreement.AgreementCreationResponse;
import com.academy.fintech.agreement.AgreementServiceGrpc;
import com.academy.fintech.pe.core.agreement.service.AgreementSchedulingService;
import com.academy.fintech.pe.core.agreement.service.AgreementCreationService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

import java.util.UUID;

@GRpcService
@RequiredArgsConstructor
public class AgreementController extends AgreementServiceGrpc.AgreementServiceImplBase {
    private final AgreementMapper agreementMapper;
    private final AgreementCreationService agreementCreationService;
    private final AgreementSchedulingService agreementSchedulingService;

    @Override
    public void create(AgreementCreationRequest request,
                       StreamObserver<AgreementCreationResponse> responseObserver) {
        UUID createdAgreementId =
                agreementCreationService.create(agreementMapper.mapCreationRequestToDto(request));
        responseObserver.onNext(
                AgreementCreationResponse.newBuilder()
                        .setAgreementId(createdAgreementId.toString())
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
