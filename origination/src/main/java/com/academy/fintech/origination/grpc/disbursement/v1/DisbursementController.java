package com.academy.fintech.origination.grpc.disbursement.v1;

import com.academy.fintech.origination.core.pe.client.ProductEngineClientService;
import com.academy.fintech.pg.ConfirmationRequest;
import com.academy.fintech.pg.DisbursementServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

@Slf4j
@GRpcService
@AllArgsConstructor
public class DisbursementController extends DisbursementServiceGrpc.DisbursementServiceImplBase {
    private final ProductEngineClientService productEngineClientService;
    @Override
    public void confirmDisbursement(ConfirmationRequest request, StreamObserver<Empty> responseObserver) {
        log.info("Got disbursement confirmation request: {}", request);
        productEngineClientService.activateAgreement(
                DisbursementMapper.mapRequestToActivationDto(request)
        );
        log.info("Sent activation request for agreement {}", request.getAgreementId());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
