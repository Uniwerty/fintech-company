package com.academy.fintech.pe.grpc.payment.v1;

import com.academy.fintech.pe.public_interface.agreement.dto.AgreementPaymentDto;
import com.academy.fintech.pg.AgreementPaymentRequest;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class PaymentMapper {
    public AgreementPaymentDto mapRequestToDto(AgreementPaymentRequest request) {
        return AgreementPaymentDto.builder()
                .agreementId(UUID.fromString(request.getAgreementId()))
                .amount(new BigDecimal(request.getAmount()))
                .build();
    }
}
