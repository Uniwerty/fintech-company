package com.academy.fintech.origination.grpc.application.v1;

import com.academy.fintech.application.ApplicationCreationError;
import com.academy.fintech.application.ApplicationCreationRequest;
import com.academy.fintech.application.ApplicationCreationResponse;
import com.academy.fintech.application.ApplicationServiceGrpc;
import com.academy.fintech.origination.core.application.service.ApplicationCreationService;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCreationResult;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.protobuf.ProtoUtils;
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
        ApplicationCreationResult result = applicationCreationService.create(
                applicationMapper.mapCreationRequestToDto(request)
        );
        if (result.isSuccess()) {
            responseObserver.onNext(
                    ApplicationCreationResponse.newBuilder()
                            .setApplicationId(result.applicationId().toString())
                            .build()
            );
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(
                    Status.ALREADY_EXISTS.asRuntimeException(createErrorMetadata(result.applicationId()))
            );
        }
    }

    private static Metadata createErrorMetadata(UUID existingApplicationId) {
        Metadata metadata = new Metadata();
        metadata.put(
                ProtoUtils.keyForProto(ApplicationCreationError.getDefaultInstance()),
                ApplicationCreationError.newBuilder()
                        .setExistingApplicationId(existingApplicationId.toString())
                        .build()
        );
        return metadata;
    }

}
