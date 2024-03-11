package com.academy.fintech.pg.grpc.pg.v1;

import com.academy.fintech.pg.DisbursementRequest;
import com.academy.fintech.pg.DisbursementResponse;
import com.academy.fintech.pg.DisbursementServiceGrpc;
import com.academy.fintech.pg.core.mp.client.MerchantProviderClientService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

@Slf4j
@GRpcService
@RequiredArgsConstructor
public class DisbursementController extends DisbursementServiceGrpc.DisbursementServiceImplBase {
    private final MerchantProviderClientService merchantProviderClientService;

    @Override
    public void makeDisbursement(DisbursementRequest request,
                                 StreamObserver<DisbursementResponse> responseObserver) {
        log.info("Got disbursement request {}", request);
        merchantProviderClientService
                .disburse(DisbursementMapper.mapRequestToDto(request))
                .subscribe(disbursementDate -> {
                            responseObserver.onNext(
                                    DisbursementResponse.newBuilder()
                                            .setDate(disbursementDate.getTime())
                                            .build()
                            );
                            responseObserver.onCompleted();
                            log.info("Sent disbursement response");
                        }
                );
    }
}
