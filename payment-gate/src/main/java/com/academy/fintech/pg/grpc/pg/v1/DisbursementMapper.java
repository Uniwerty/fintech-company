package com.academy.fintech.pg.grpc.pg.v1;

import com.academy.fintech.pg.DisbursementRequest;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementCreationDto;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementDto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class DisbursementMapper {
    public static DisbursementCreationDto mapRequestToCreationDto(DisbursementRequest request) {
        return DisbursementCreationDto.builder()
                .clientId(UUID.fromString(request.getClientId()))
                .amount(new BigDecimal(request.getAmount()))
                .build();
    }

    public static DisbursementDto mapRequestToDto(DisbursementRequest request) {
        return DisbursementDto.builder()
                .clientId(UUID.fromString(request.getClientId()))
                .amount(new BigDecimal(request.getAmount()))
                .build();
    }
}
