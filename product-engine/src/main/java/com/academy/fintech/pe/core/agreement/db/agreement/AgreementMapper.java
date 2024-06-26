package com.academy.fintech.pe.core.agreement.db.agreement;

import com.academy.fintech.pe.core.agreement.db.agreement.model.Agreement;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementCreationDto;
import com.academy.fintech.pe.public_interface.schedule.dto.PaymentScheduleCreationDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AgreementMapper {
    public static Agreement mapAgreementCreationDtoToEntity(AgreementCreationDto agreementCreationDto) {
        return Agreement.builder()
                .productCode(agreementCreationDto.productCode())
                .clientId(agreementCreationDto.clientId())
                .term(agreementCreationDto.term())
                .interest(agreementCreationDto.interest())
                .principalAmount(agreementCreationDto.principalAmount())
                .disbursementAmount(agreementCreationDto.disbursementAmount())
                .originationAmount(agreementCreationDto.originationAmount())
                .build();
    }

    public static PaymentScheduleCreationDto mapEntityToPaymentScheduleCreationDto(Agreement agreement) {
        return PaymentScheduleCreationDto.builder()
                .agreementId(agreement.getId())
                .principalAmount(agreement.getPrincipalAmount())
                .interest(agreement.getInterest())
                .term(agreement.getTerm())
                .disbursementDate(agreement.getDisbursementDate())
                .build();
    }
}
