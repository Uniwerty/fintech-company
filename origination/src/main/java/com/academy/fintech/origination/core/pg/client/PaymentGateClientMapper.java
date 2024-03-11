package com.academy.fintech.origination.core.pg.client;

import com.academy.fintech.origination.public_interface.payment.dto.DisbursementRequestDto;
import com.academy.fintech.pg.DisbursementRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentGateClientMapper {
    public DisbursementRequest mapDtoToRequest(DisbursementRequestDto requestDto) {
        return DisbursementRequest.newBuilder()
                .setClientId(requestDto.clientId().toString())
                .setAmount(requestDto.amount().toString())
                .build();
    }
}
