package com.academy.fintech.origination.core.scoring.client;

import com.academy.fintech.origination.core.scoring.client.grpc.ScoringGrpcClient;
import com.academy.fintech.origination.public_interface.application.dto.ScoringRequestDto;
import com.academy.fintech.scoring.ScoringRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoringClientService {
    private final ScoringGrpcClient scoringGrpcClient;

    public boolean getApprovalVerdict(ScoringRequestDto requestDto) {
        return scoringGrpcClient.getApprovalVerdict(mapDtoToRequest(requestDto)).getApproved();
    }

    private static ScoringRequest mapDtoToRequest(ScoringRequestDto requestDto) {
        return ScoringRequest.newBuilder()
                .setClientId(requestDto.clientId().toString())
                .setSalary(requestDto.salary().toString())
                .setRequestedAmount(requestDto.requestedAmount().toString())
                .build();
    }
}