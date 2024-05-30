package com.academy.fintech.pg.core.disbursement.db.disbursement;

import com.academy.fintech.pg.core.disbursement.db.disbursement.model.Disbursement;
import com.academy.fintech.pg.core.disbursement.db.disbursement.model.DisbursementStatus;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementCreationDto;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DisbursementMapper {
    public static Disbursement mapCreationDtoToEntity(DisbursementCreationDto dto) {
        return Disbursement.builder()
                .agreementId(dto.agreementId())
                .clientId(dto.clientId())
                .status(DisbursementStatus.PROCESSING)
                .amount(dto.amount())
                .build();
    }

    public static DisbursementDto mapEntityToDto(Disbursement disbursement) {
        return DisbursementDto.builder()
                .agreementId(disbursement.getAgreementId())
                .clientId(disbursement.getClientId())
                .amount(disbursement.getAmount())
                .build();
    }
}
