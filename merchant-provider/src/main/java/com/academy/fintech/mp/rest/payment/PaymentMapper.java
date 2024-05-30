package com.academy.fintech.mp.rest.payment;

import com.academy.fintech.mp.public_interface.dto.disbursement.DisbursementCreationDto;
import com.academy.fintech.mp.public_interface.dto.disbursement.DisbursementResultDto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class PaymentMapper {
    public static DisbursementCreationDto mapRequestToCreationDto(DisbursementRequest request) {
        return DisbursementCreationDto.builder()
                .agreementId(UUID.fromString(request.agreementId()))
                .clientId(UUID.fromString(request.clientId()))
                .amount(new BigDecimal(request.amount()))
                .build();
    }
    public static DisbursementResponse mapResultDtoToResponse(DisbursementResultDto resultDto) {
        return DisbursementResponse.builder()
                .isCompleted(resultDto.isCompleted())
                .date(resultDto.date().getTime())
                .build();
    }
}
