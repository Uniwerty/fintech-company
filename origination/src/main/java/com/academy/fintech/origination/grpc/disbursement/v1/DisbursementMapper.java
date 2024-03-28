package com.academy.fintech.origination.grpc.disbursement.v1;

import com.academy.fintech.origination.public_interface.agreement.dto.AgreementActivationDto;
import com.academy.fintech.pg.ConfirmationRequest;
import lombok.experimental.UtilityClass;

import java.sql.Date;
import java.util.UUID;

@UtilityClass
public class DisbursementMapper {
    public AgreementActivationDto mapRequestToActivationDto(ConfirmationRequest request) {
        return AgreementActivationDto.builder()
                .id(UUID.fromString(request.getAgreementId()))
                .disbursementDate(new Date(request.getDisbursementDate()))
                .build();
    }
}
