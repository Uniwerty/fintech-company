package com.academy.fintech.origination.grpc.application.v1;

import com.academy.fintech.application.ApplicationCreationRequest;
import com.academy.fintech.application.ApplicationCreationResponse;
import com.academy.fintech.application.ApplicationServiceGrpc;
import com.academy.fintech.origination.core.application.service.ApplicationCreationService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

import java.util.UUID;

@Slf4j
@GRpcService
@RequiredArgsConstructor
public class ApplicationController extends ApplicationServiceGrpc.ApplicationServiceImplBase {
    private final ApplicationMapper applicationMapper;
    private final ApplicationCreationService applicationCreationService;

    @Override
    public void create(ApplicationCreationRequest request,
                       StreamObserver<ApplicationCreationResponse> responseObserver) {
        log.info("Application creation request received: {}", request);
        UUID applicationId = applicationCreationService.create(
                applicationMapper.mapCreationRequestToDto(request)
        );
        responseObserver.onNext(
                ApplicationCreationResponse.newBuilder()
                        .setApplicationId(applicationId.toString())
                        .build()
        );
        responseObserver.onCompleted();
    }

}
