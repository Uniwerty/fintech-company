package com.academy.fintech.pg.rest.payment;

import com.academy.fintech.pg.public_interface.payment.dto.AgreementPaymentDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentMapper {
    public static AgreementPaymentDto mapRequestToDto(PaymentRequest request) {
        return AgreementPaymentDto.builder()
                .agreementId(request.agreementId())
                .amount(request.amount())
                .build();
    }
}
