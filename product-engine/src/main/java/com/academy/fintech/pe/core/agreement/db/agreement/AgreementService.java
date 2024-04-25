package com.academy.fintech.pe.core.agreement.db.agreement;

import com.academy.fintech.pe.core.agreement.db.agreement.model.Agreement;
import com.academy.fintech.pe.core.agreement.db.agreement.repository.AgreementRepository;
import com.academy.fintech.pe.core.agreement.db.outbox.AgreementMessageMapper;
import com.academy.fintech.pe.core.agreement.db.outbox.AgreementMessageService;
import com.academy.fintech.pe.core.agreement.status.AgreementStatus;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementActivationDto;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementCreationDto;
import com.academy.fintech.pe.public_interface.schedule.dto.PaymentScheduleCreationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgreementService {
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(3);
    private final AgreementRepository agreementRepository;
    private final AgreementMessageService messageService;

    @Transactional
    public UUID create(AgreementCreationDto agreementCreationDto) {
        Agreement agreement = AgreementMapper.mapAgreementCreationDtoToEntity(agreementCreationDto);
        agreement.setStatus(AgreementStatus.NEW.name());
        agreement = agreementRepository.save(agreement);
        try {
            messageService.save(AgreementMessageMapper.mapAgreementToMessage(agreement));
        } catch (JsonProcessingException e) {
            log.error("Couldn't process JSON: {}", e.getMessage());
        }
        return agreement.getId();
    }

    @Transactional
    public PaymentScheduleCreationDto activate(AgreementActivationDto agreementActivationDto) {
        Agreement agreement = agreementRepository.findById(agreementActivationDto.id()).orElseThrow();
        agreement.setStatus(AgreementStatus.ACTIVE.name());
        agreement.setDisbursementDate(agreementActivationDto.disbursementDate());
        agreement = agreementRepository.save(agreement);
        try {
            messageService.save(AgreementMessageMapper.mapAgreementToMessage(agreement));
        } catch (JsonProcessingException e) {
            log.error("Couldn't process JSON: {}", e.getMessage());
        }
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

    public List<UUID> findActiveAgreementsByNextPaymentDate(Date currentDate) {
        return agreementRepository
                .findAgreementsByStatusAndNextPaymentDate(
                        AgreementStatus.ACTIVE.name(),
                        currentDate
                )
                .stream()
                .map(Agreement::getId)
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
