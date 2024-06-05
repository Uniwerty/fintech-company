package com.academy.fintech.dwh.core.agreement.db;

import com.academy.fintech.dwh.core.agreement.db.model.Agreement;
import com.academy.fintech.dwh.public_interface.agreement.AgreementDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AgreementMapper {
    public static Agreement mapDtoToEntity(AgreementDto dto) {
        return Agreement.builder()
                .id(dto.id())
                .productCode(dto.productCode())
                .clientId(dto.clientId())
                .status(dto.status())
                .term(dto.term())
                .interest(dto.interest())
                .principalAmount(dto.principalAmount())
                .disbursementAmount(dto.disbursementAmount())
                .originationAmount(dto.originationAmount())
                .disbursementDate(dto.disbursementDate())
                .nextPaymentDate(dto.nextPaymentDate())
                .build();
    }
}
