package com.academy.fintech.pg.core.pe.client;

import com.academy.fintech.pg.core.pe.client.grpc.AgreementPaymentClient;
import com.academy.fintech.pg.public_interface.payment.dto.AgreementPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEngineClientService {
    private final AgreementPaymentClient agreementPaymentClient;

    public boolean payForAgreement(AgreementPaymentDto agreementPaymentDto) {
        return agreementPaymentClient
                .pay(ProductEngineClientMapper.mapDtoToRequest(agreementPaymentDto))
                .getIsCompleted();
    }
}
