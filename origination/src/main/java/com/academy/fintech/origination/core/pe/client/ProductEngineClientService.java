package com.academy.fintech.origination.core.pe.client;

import com.academy.fintech.origination.core.pe.client.grpc.AgreementServiceClient;
import com.academy.fintech.origination.core.pe.client.grpc.ProductServiceClient;
import com.academy.fintech.origination.public_interface.agreement.dto.AgreementActivationDto;
import com.academy.fintech.origination.public_interface.agreement.dto.AgreementCreationDto;
import com.academy.fintech.origination.public_interface.product.dto.ProductDto;
import com.academy.fintech.product.ProductParametersRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductEngineClientService {
    private final AgreementServiceClient agreementServiceClient;
    private final ProductServiceClient productServiceClient;

    public UUID createAgreement(AgreementCreationDto requestDto) {
        return UUID.fromString(
                agreementServiceClient
                        .create(ProductEngineClientMapper.mapCreationDtoToRequest(requestDto))
                        .getAgreementId()
        );
    }

    public void activateAgreement(AgreementActivationDto requestDto) {
        agreementServiceClient.activate(
                ProductEngineClientMapper.mapActivationDtoToRequest(requestDto)
        );
    }

    public ProductDto getProductParameters(String productCode) {
        return ProductEngineClientMapper.mapProductResponseToDto(
                productServiceClient.getProductParameters(
                        ProductParametersRequest.newBuilder()
                                .setProductCode(productCode)
                                .build()
                )
        );
    }
}
