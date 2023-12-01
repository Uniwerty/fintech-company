package com.academy.fintech.api.core.origination.client;

import com.academy.fintech.api.core.origination.client.grpc.OriginationGrpcClient;
import com.academy.fintech.api.public_interface.application.dto.ApplicationDto;
import com.academy.fintech.application.ApplicationCancellationError;
import com.academy.fintech.application.ApplicationCancellationRequest;
import com.academy.fintech.application.ApplicationCancellationResponse;
import com.academy.fintech.application.ApplicationCreationError;
import com.academy.fintech.application.ApplicationCreationRequest;
import com.academy.fintech.application.ApplicationCreationResponse;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OriginationClientService {
    private static final String UNKNOWN_RESULT = "Unknown result";

    private final OriginationGrpcClient originationGrpcClient;

    public String createApplication(ApplicationDto applicationDto) {
        ApplicationCreationRequest request = OriginationClientMapper.mapDtoToCreationRequest(applicationDto);
        try {
            ApplicationCreationResponse response = originationGrpcClient.createApplication(request);
            return response.getApplicationId();
        } catch (StatusRuntimeException e) {
            if (e.getTrailers() != null) {
                ApplicationCreationError error = e.getTrailers()
                        .get(ProtoUtils.keyForProto(ApplicationCreationError.getDefaultInstance()));
                if (error != null) {
                    return error.getExistingApplicationId();
                }
            }
            return UNKNOWN_RESULT;
        }
    }

    public String cancelApplication(String applicationId) {
        try {
            ApplicationCancellationResponse response = originationGrpcClient.cancelApplication(
                    ApplicationCancellationRequest.newBuilder()
                            .setApplicationId(applicationId)
                            .build()
            );
            return "Canceled successfully";
        } catch (StatusRuntimeException e) {
            if (e.getTrailers() != null) {
                ApplicationCancellationError error =
                        e.getTrailers()
                                .get(ProtoUtils.keyForProto(ApplicationCancellationError.getDefaultInstance()));
                if (error != null) {
                    return error.getMessage();
                }
            }
            return UNKNOWN_RESULT;
        }
    }
}
