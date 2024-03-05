package com.academy.fintech.scoring.grpc.scoring.v1;

import com.academy.fintech.scoring.ScoringRequest;
import com.academy.fintech.scoring.ScoringResponse;
import com.academy.fintech.scoring.ScoringServiceGrpc;
import com.academy.fintech.scoring.core.service.ScoringService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

@Slf4j
@GRpcService
@RequiredArgsConstructor
public class ScoringController extends ScoringServiceGrpc.ScoringServiceImplBase {
    private final ScoringService scoringService;

    @Override
    public void getScore(ScoringRequest request, StreamObserver<ScoringResponse> responseObserver) {
        log.info("Scoring request for client {} received", request.getClientId());
        boolean approved = scoringService.getApprovalVerdict(ScoringMapper.mapRequestToDto(request));
        responseObserver.onNext(
                ScoringResponse.newBuilder()
                        .setClientId(request.getClientId())
                        .setApproved(approved)
                        .build()
        );
        responseObserver.onCompleted();
        log.info("Scoring result sent");
    }
}
