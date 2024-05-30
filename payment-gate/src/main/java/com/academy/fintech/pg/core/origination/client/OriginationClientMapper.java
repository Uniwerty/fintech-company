package com.academy.fintech.pg.core.origination.client;

import com.academy.fintech.pg.ConfirmationRequest;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementConfirmationDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OriginationClientMapper {
    public ConfirmationRequest mapDtoToRequest(DisbursementConfirmationDto confirmationDto) {
        return ConfirmationRequest.newBuilder()
                .setAgreementId(confirmationDto.agreementId().toString())
                .setDisbursementDate(confirmationDto.disbursementDate().getTime())
                .build();
    }
}
