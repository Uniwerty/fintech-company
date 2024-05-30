package com.academy.fintech.pg.core.mp.client;

import com.academy.fintech.pg.core.mp.client.rest.DisbursementResultRequest;
import com.academy.fintech.pg.core.mp.client.rest.MerchantProviderClient;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementDto;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchantProviderClientService {
    private final MerchantProviderClient merchantProviderClient;

    public void disburse(DisbursementDto disbursementDto) {
        merchantProviderClient.disburse(
                MerchantProviderClientMapper.mapDtoToRequest(disbursementDto)
        );
    }

    public Mono<DisbursementResultDto> getDisbursementResult(UUID disbursementId) {
        return merchantProviderClient
                .getDisbursementResult(new DisbursementResultRequest(disbursementId))
                .map(MerchantProviderClientMapper::mapResultResponseToDto);
    }
}
