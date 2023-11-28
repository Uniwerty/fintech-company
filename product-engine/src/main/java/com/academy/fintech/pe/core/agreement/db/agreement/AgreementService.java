package com.academy.fintech.pe.core.agreement.db.agreement;

import com.academy.fintech.pe.core.agreement.db.agreement.model.Agreement;
import com.academy.fintech.pe.core.agreement.db.agreement.repository.AgreementRepository;
import com.academy.fintech.pe.core.agreement.status.AgreementStatus;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementActivationDto;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementCreationDto;
import com.academy.fintech.pe.public_interface.schedule.dto.PaymentScheduleCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgreementService {
    private final AgreementRepository agreementRepository;

    public UUID create(AgreementCreationDto agreementCreationDto) {
        Agreement agreement = AgreementMapper.mapAgreementCreationDtoToEntity(agreementCreationDto);
        agreement.setStatus(AgreementStatus.NEW.name());
        return agreementRepository.save(agreement).getId();
    }

    public PaymentScheduleCreationDto activate(AgreementActivationDto agreementActivationDto) {
        Agreement agreement = agreementRepository.findById(agreementActivationDto.id()).orElseThrow();
        agreement.setStatus(AgreementStatus.ACTIVE.name());
        agreement.setDisbursementDate(agreementActivationDto.disbursementDate());
        agreementRepository.save(agreement);
        return AgreementMapper.mapEntityToPaymentScheduleCreationDto(agreement);
    }

    public void setNextPaymentDate(UUID agreementId, Date nextPaymentDate) {
        agreementRepository.updateAgreementNextPaymentDate(agreementId, nextPaymentDate);
    }
}
