package com.academy.fintech.mp.disbursement.db;

import com.academy.fintech.mp.disbursement.db.model.Disbursement;
import com.academy.fintech.mp.disbursement.db.model.DisbursementStatus;
import com.academy.fintech.mp.public_interface.dto.disbursement.DisbursementCreationDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DisbursementMapper {
    public static Disbursement mapCreationDtoToEntity(DisbursementCreationDto creationDto) {
        return Disbursement.builder()
                .agreementId(creationDto.agreementId())
                .clientId(creationDto.clientId())
                .status(DisbursementStatus.PROCESSING)
                .amount(creationDto.amount())
                .build();
    }
}
