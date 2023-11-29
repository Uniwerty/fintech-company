package com.academy.fintech.pe.core.agreement.db.agreement;

import com.academy.fintech.pe.core.agreement.db.agreement.model.Agreement;
import com.academy.fintech.pe.core.agreement.db.agreement.repository.AgreementRepository;
import com.academy.fintech.pe.core.agreement.status.AgreementStatus;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementActivationDto;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementCreationDto;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementDto;
import com.academy.fintech.pe.public_interface.schedule.dto.PaymentScheduleCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgreementService {
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(3);
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

    public List<OffsetDateTime> getClientNextPaymentDates(UUID clientId) {
        return agreementRepository
                .findAgreementsByClientId(clientId)
                .stream()
                .map(Agreement::getNextPaymentDate)
                .map(AgreementService::offsetDateTimeOf)
                .toList();
    }

    private static OffsetDateTime offsetDateTimeOf(Date date) {
        return OffsetDateTime.of(
                date.toLocalDate(),
                LocalTime.MIDNIGHT,
                ZONE_OFFSET
        );
    }
}
