package com.academy.fintech.pg.core.mp.client;

import com.academy.fintech.pg.core.mp.client.rest.MerchantProviderClient;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class MerchantProviderClientService {
    private final MerchantProviderClient merchantProviderClient;

    public Mono<Date> disburse(DisbursementDto disbursementDto) {
        return merchantProviderClient
                .disburse(MerchantProviderClientMapper.mapDtoToRequest(disbursementDto))
                .map(response -> new Date(response.disbursementDate()));
    }
}
