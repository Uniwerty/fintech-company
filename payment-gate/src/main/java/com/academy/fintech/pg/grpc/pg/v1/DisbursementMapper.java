package com.academy.fintech.pg.grpc.pg.v1;

import com.academy.fintech.pg.DisbursementRequest;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DisbursementMapper {
    public static DisbursementDto mapRequestToDto(DisbursementRequest request) {
        return DisbursementDto.builder()
                .clientId(request.getClientId())
                .amount(request.getAmount())
                .build();
    }
}
