package com.academy.fintech.pe.grpc.agreement.v1;

import com.academy.fintech.agreement.AgreementCreationRequest;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AgreementMapper {
    public AgreementDto mapCreationRequestToDto(AgreementCreationRequest request) {
        return AgreementDto.builder()
                .productCode(request.getProductCode())
                .clientId(request.getClientId())
                .loanTerm(request.getLoanTerm())
                .interest(new BigDecimal(request.getInterest()))
                .disbursementAmount(new BigDecimal(request.getDisbursementAmount()))
                .originationAmount(new BigDecimal(request.getOriginationAmount()))
                .build();
    }
}
