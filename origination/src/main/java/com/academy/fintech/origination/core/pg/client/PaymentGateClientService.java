package com.academy.fintech.origination.core.pg.client;

import com.academy.fintech.origination.core.pg.client.grpc.PaymentGateClient;
import com.academy.fintech.origination.public_interface.payment.dto.PaymentRequestDto;
import com.academy.fintech.pg.DisbursementRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class PaymentGateClientService {
    private final PaymentGateClient paymentGateClient;

    public Date makeDisbursement(PaymentRequestDto requestDto) {
        return new Date(
                paymentGateClient
                        .makeDisbursement(
                                DisbursementRequest.newBuilder()
                                        .setClientId(requestDto.clientId().toString())
                                        .setAmount(requestDto.amount().toString())
                                        .build()
                        )
                        .getDate()
        );
    }
}
