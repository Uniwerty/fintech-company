package com.academy.fintech.pg.core.mp.client;

import com.academy.fintech.pg.core.mp.client.rest.DisbursementRequest;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MerchantProviderClientMapper {
    public static DisbursementRequest mapDtoToRequest(DisbursementDto disbursementDto) {
        return DisbursementRequest.builder()
                .clientId(disbursementDto.clientId())
                .amount(disbursementDto.amount())
                .build();
    }
}
