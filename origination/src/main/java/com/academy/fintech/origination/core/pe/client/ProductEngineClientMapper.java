package com.academy.fintech.origination.core.pe.client;

import com.academy.fintech.agreement.AgreementActivationRequest;
import com.academy.fintech.agreement.AgreementCreationRequest;
import com.academy.fintech.origination.public_interface.agreement.dto.AgreementActivationDto;
import com.academy.fintech.origination.public_interface.agreement.dto.AgreementCreationDto;
import com.academy.fintech.origination.public_interface.product.dto.ProductDto;
import com.academy.fintech.product.ProductParametersResponse;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class ProductEngineClientMapper {
    public static AgreementCreationRequest mapCreationDtoToRequest(AgreementCreationDto requestDto) {
        return AgreementCreationRequest.newBuilder()
                .setClientId(requestDto.clientId().toString())
                .setProductCode(requestDto.productCode())
                .setDisbursementAmount(requestDto.disbursementAmount().toString())
                .setOriginationAmount(requestDto.originationAmount().toString())
                .setInterest(requestDto.interest().toString())
                .setTerm(requestDto.term())
                .build();
    }

    public static AgreementActivationRequest mapActivationDtoToRequest(AgreementActivationDto requestDto) {
        return AgreementActivationRequest.newBuilder()
                .setAgreementId(requestDto.id().toString())
                .setDisbursementDate(requestDto.disbursementDate().getTime())
                .build();
    }

    public static ProductDto mapProductResponseToDto(ProductParametersResponse response) {
        return ProductDto.builder()
                .originationAmount(new BigDecimal(response.getOriginationAmount()))
                .interest(new BigDecimal(response.getInterest()))
                .term(response.getTerm())
                .build();
    }
}
