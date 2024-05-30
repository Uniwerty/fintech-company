package com.academy.fintech.pg.grpc.pg.v1;

import com.academy.fintech.pg.DisbursementRequest;
import com.academy.fintech.pg.DisbursementServiceGrpc;
import com.academy.fintech.pg.core.disbursement.service.DisbursementControlService;
import com.academy.fintech.pg.core.mp.client.MerchantProviderClientService;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementDto;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

@Slf4j
@GRpcService
@RequiredArgsConstructor
public class DisbursementController extends DisbursementServiceGrpc.DisbursementServiceImplBase {
    private final MerchantProviderClientService merchantProviderClientService;
    private final DisbursementControlService disbursementControlService;

    @Override
    public void makeDisbursement(DisbursementRequest request,
                                 StreamObserver<Empty> responseObserver) {
        log.info("Got disbursement request {}", request);
        DisbursementDto disbursementDto = disbursementControlService.createDisbursement(
                DisbursementMapper.mapRequestToCreationDto(request)
        );
        merchantProviderClientService.disburse(disbursementDto);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
