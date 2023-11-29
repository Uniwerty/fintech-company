package com.academy.fintech.pe.grpc.scoring.v1;

import com.academy.fintech.pe.core.application.ApplicationService;
import com.academy.fintech.scoring.ApplicationCalculationRequest;
import com.academy.fintech.scoring.ApplicationCalculationResponse;
import com.academy.fintech.scoring.ApplicationCalculationServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@GRpcService
@RequiredArgsConstructor
public class ApplicationCalculationController extends ApplicationCalculationServiceGrpc.ApplicationCalculationServiceImplBase {
    private final ApplicationService applicationService;

    @Override
    public void getApplicationInfo(ApplicationCalculationRequest request,
                                   StreamObserver<ApplicationCalculationResponse> responseObserver) {
        log.info("Client payment info request for {} received", request.getClientId());
        BigDecimal periodPayment = applicationService.getApplicationPeriodPayment(
                ApplicationCalculationMapper.mapRequestToDto(request)
        );
        int overdue = applicationService.getClientOverdue(UUID.fromString(request.getClientId()));
        responseObserver.onNext(
                ApplicationCalculationResponse.newBuilder()
                        .setClientId(request.getClientId())
                        .setPeriodPayment(periodPayment.toString())
                        .setDaysOverdue(overdue)
                        .build()
        );
        responseObserver.onCompleted();
        log.info("Client payment info response sent");
    }
}
