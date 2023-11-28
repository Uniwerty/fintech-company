package com.academy.fintech.pe.grpc.agreement.v1;

import com.academy.fintech.agreement.AgreementActivationRequest;
import com.academy.fintech.agreement.AgreementCreationRequest;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementActivationDto;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementCreationDto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@UtilityClass
public class AgreementMapper {
    public static AgreementCreationDto mapCreationRequestToDto(AgreementCreationRequest request) {
        BigDecimal disbursementAmount = new BigDecimal(request.getDisbursementAmount());
        return AgreementCreationDto.builder()
                .productCode(request.getProductCode())
                .clientId(UUID.fromString(request.getClientId()))
                .term(request.getTerm())
                .interest(new BigDecimal(request.getInterest()))
                .principalAmount(disbursementAmount)
                .disbursementAmount(disbursementAmount)
                .originationAmount(new BigDecimal(request.getOriginationAmount()))
                .build();
    }

    public static AgreementActivationDto mapActivationRequestToDto(AgreementActivationRequest request) {
        return AgreementActivationDto.builder()
                .id(UUID.fromString(request.getAgreementId()))
                .disbursementDate(new Date(request.getDisbursementDate()))
                .build();
    }
}
