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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@GRpcService
@RequiredArgsConstructor
public class AgreementController extends AgreementServiceGrpc.AgreementServiceImplBase {
    private final AgreementMapper agreementMapper;
    private final AgreementCreationService agreementCreationService;
    private final AgreementSchedulingService agreementSchedulingService;
    private final Logger logger = LoggerFactory.getLogger(AgreementController.class);

    @Override
    public void create(AgreementCreationRequest request,
                       StreamObserver<AgreementCreationResponse> responseObserver) {
        logger.info("Agreement creation request for client {} received", request.getClientId());
        UUID createdAgreementId =
                agreementCreationService.create(agreementMapper.mapCreationRequestToDto(request));
        logger.info("New agreement created successfully");
        responseObserver.onNext(
                AgreementCreationResponse.newBuilder()
                        .setAgreementId(createdAgreementId.toString())
                        .build()
        );
        responseObserver.onCompleted();
        logger.info("Agreement creation response sent");
    }

    @Override
    public void activate(AgreementActivationRequest request,
                         StreamObserver<AgreementActivationResponse> responseObserver) {
        logger.info("Agreement {} activation request received", request.getAgreementId());
        UUID createdScheduleId =
                agreementSchedulingService.activate(agreementMapper.mapActivationRequestToDto(request));
        logger.info("Agreement activated successfully");
        responseObserver.onNext(
                AgreementActivationResponse.newBuilder()
                        .setScheduleId(createdScheduleId.toString())
                        .build()
        );
        responseObserver.onCompleted();
        logger.info("Agreement activation response sent");
    }
}
