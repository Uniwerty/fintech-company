package com.academy.fintech.api.core.origination.client.grpc;

import com.academy.fintech.application.ApplicationCancellationRequest;
import com.academy.fintech.application.ApplicationCancellationResponse;
import com.academy.fintech.application.ApplicationCreationRequest;
import com.academy.fintech.application.ApplicationCreationResponse;
import com.academy.fintech.application.ApplicationServiceGrpc;
import com.academy.fintech.application.ApplicationServiceGrpc.ApplicationServiceBlockingStub;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OriginationGrpcClient {

    private final ApplicationServiceBlockingStub stub;

    public OriginationGrpcClient(OriginationGrpcClientProperty property) {
        Channel channel = ManagedChannelBuilder.forAddress(property.host(), property.port()).usePlaintext().build();
        this.stub = ApplicationServiceGrpc.newBlockingStub(channel);
    }

    public ApplicationCreationResponse createApplication(ApplicationCreationRequest request) {
        try {
            return stub.create(request);
        } catch (StatusRuntimeException e) {
            log.error("Got error from Origination by creation request: {}", request, e);
            throw e;
        }
    }

    public ApplicationCancellationResponse cancelApplication(ApplicationCancellationRequest request) {
        try {
            return stub.cancel(request);
        } catch (StatusRuntimeException e) {
            log.error("Got error from Origination by cancellation request: {}", request, e);
            throw e;
        }
    }
}
