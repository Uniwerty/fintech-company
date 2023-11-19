package com.academy.fintech.pe.grpc.agreement.v1;

import com.academy.fintech.agreement.AgreementActivationRequest;
import com.academy.fintech.agreement.AgreementCreationRequest;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementActivationDto;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementCreationDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Component("grpc.mapper")
public class AgreementMapper {
    public AgreementCreationDto mapCreationRequestToDto(AgreementCreationRequest request) {
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
}
