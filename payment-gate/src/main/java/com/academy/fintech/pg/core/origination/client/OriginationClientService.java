package com.academy.fintech.pg.core.origination.client;

import com.academy.fintech.pg.core.origination.client.grpc.OriginationClient;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementConfirmationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OriginationClientService {
    private final OriginationClient originationClient;

    public void confirmDisbursement(DisbursementConfirmationDto confirmationDto) {
        originationClient.confirmDisbursement(
                OriginationClientMapper.mapDtoToRequest(confirmationDto)
        );
    }
}
