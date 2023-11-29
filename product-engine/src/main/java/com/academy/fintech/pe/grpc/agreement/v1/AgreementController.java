package com.academy.fintech.pe.grpc.agreement.v1;

import com.academy.fintech.agreement.AgreementActivationRequest;
import com.academy.fintech.agreement.AgreementActivationResponse;
import com.academy.fintech.agreement.AgreementCreationRequest;
import com.academy.fintech.agreement.AgreementCreationResponse;
import com.academy.fintech.agreement.AgreementServiceGrpc;
import com.academy.fintech.pe.core.agreement.service.AgreementCreationService;
import com.academy.fintech.pe.core.agreement.service.AgreementSchedulingService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

import java.util.UUID;

@Slf4j
@GRpcService
@RequiredArgsConstructor
public class AgreementController extends AgreementServiceGrpc.AgreementServiceImplBase {
    private final AgreementCreationService agreementCreationService;
    private final AgreementSchedulingService agreementSchedulingService;

    @Override
    public void create(AgreementCreationRequest request,
                       StreamObserver<AgreementCreationResponse> responseObserver) {
        log.info("Agreement creation request for client {} received", request.getClientId());
        UUID createdAgreementId =
                agreementCreationService.create(AgreementMapper.mapCreationRequestToDto(request));
        log.info("New agreement created successfully");
        responseObserver.onNext(
                AgreementCreationResponse.newBuilder()
                        .setAgreementId(createdAgreementId.toString())
                        .build()
        );
        responseObserver.onCompleted();
        log.info("Agreement creation response sent");
    }

    @Override
    public void activate(AgreementActivationRequest request,
                         StreamObserver<AgreementActivationResponse> responseObserver) {
        log.info("Agreement {} activation request received", request.getAgreementId());
        UUID createdScheduleId =
                agreementSchedulingService.activate(AgreementMapper.mapActivationRequestToDto(request));
        log.info("Agreement activated successfully");
        responseObserver.onNext(
                AgreementActivationResponse.newBuilder()
                        .setScheduleId(createdScheduleId.toString())
                        .build()
        );
        responseObserver.onCompleted();
        log.info("Agreement activation response sent");
    }
}
