package com.academy.fintech.pg.core.mp.client;

import com.academy.fintech.pg.core.mp.client.rest.DisbursementRequest;
import com.academy.fintech.pg.core.mp.client.rest.DisbursementResultResponse;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementDto;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementResultDto;
import lombok.experimental.UtilityClass;

import java.sql.Date;

@UtilityClass
public class MerchantProviderClientMapper {
    public static DisbursementRequest mapDtoToRequest(DisbursementDto disbursementDto) {
        return DisbursementRequest.builder()
                .agreementId(disbursementDto.agreementId().toString())
                .clientId(disbursementDto.clientId().toString())
                .amount(disbursementDto.amount().toString())
                .build();
    }

    public static DisbursementResultDto mapResultResponseToDto(DisbursementResultResponse response) {
        return DisbursementResultDto.builder()
                .isCompleted(response.isCompleted())
                .disbursementDate(new Date(response.disbursementDate()))
                .build();
    }
}
