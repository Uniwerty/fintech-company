package com.academy.fintech.origination.grpc.application.v1;

import com.academy.fintech.application.ApplicationCancellationError;
import com.academy.fintech.application.ApplicationCancellationRequest;
import com.academy.fintech.application.ApplicationCancellationResponse;
import com.academy.fintech.application.ApplicationCreationError;
import com.academy.fintech.application.ApplicationCreationRequest;
import com.academy.fintech.application.ApplicationCreationResponse;
import com.academy.fintech.application.ApplicationServiceGrpc;
import com.academy.fintech.origination.core.application.service.ApplicationCancellationService;
import com.academy.fintech.origination.core.application.service.ApplicationCreationService;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCancellationResult;
import com.academy.fintech.origination.public_interface.application.dto.ApplicationCreationResult;
import com.google.protobuf.Message;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
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
    private final ApplicationCancellationService applicationCancellationService;

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
                    createError(
                            Status.ALREADY_EXISTS,
                            ApplicationCreationError.getDefaultInstance(),
                            ApplicationCreationError.newBuilder()
                                    .setExistingApplicationId(result.applicationId().toString())
                                    .build()
                    )
            );
        }
    }

    @Override
    public void cancel(ApplicationCancellationRequest request,
                       StreamObserver<ApplicationCancellationResponse> responseObserver) {
        log.info("Application cancellation request received: {}", request);
        ApplicationCancellationResult result =
                applicationCancellationService.cancel(UUID.fromString(request.getApplicationId()));
        if (result.isSuccess()) {
            responseObserver.onNext(ApplicationCancellationResponse.newBuilder().build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(
                    createError(
                            Status.UNAVAILABLE,
                            ApplicationCancellationError.getDefaultInstance(),
                            ApplicationCancellationError.newBuilder()
                                    .setMessage(result.message())
                                    .build()
                    )
            );
        }
    }

    private static <T extends Message> StatusRuntimeException createError(Status status,
                                                                          T key,
                                                                          T message) {
        Metadata metadata = new Metadata();
        metadata.put(ProtoUtils.keyForProto(key), message);
        return status.asRuntimeException(metadata);
    }
}
