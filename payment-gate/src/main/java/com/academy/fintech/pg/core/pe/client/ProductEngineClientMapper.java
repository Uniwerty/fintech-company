package com.academy.fintech.pg.core.pe.client;

import com.academy.fintech.pg.AgreementPaymentRequest;
import com.academy.fintech.pg.public_interface.payment.dto.AgreementPaymentDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductEngineClientMapper {
    public static AgreementPaymentRequest mapDtoToRequest(AgreementPaymentDto agreementPaymentDto) {
        return AgreementPaymentRequest.newBuilder()
                .setAgreementId(agreementPaymentDto.agreementId())
                .setAmount(agreementPaymentDto.amount())
                .build();
    }
}
