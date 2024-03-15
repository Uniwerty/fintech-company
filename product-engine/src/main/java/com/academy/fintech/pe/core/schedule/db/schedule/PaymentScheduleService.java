package com.academy.fintech.pe.core.schedule.db.schedule;

import com.academy.fintech.pe.core.schedule.db.schedule.model.PaymentSchedule;
import com.academy.fintech.pe.core.schedule.db.schedule.repository.PaymentScheduleRepository;
import com.academy.fintech.pe.public_interface.payment.dto.PaymentCreationDto;
import com.academy.fintech.pe.public_interface.schedule.dto.PaymentScheduleCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentScheduleService {
    private final PaymentScheduleRepository paymentScheduleRepository;

    public PaymentCreationDto create(PaymentScheduleCreationDto paymentScheduleCreationDto) {
        Optional<PaymentSchedule> previousSchedule =
                paymentScheduleRepository
                        .findLatestVersionByAgreementId(paymentScheduleCreationDto.agreementId());
        int previousVersion = previousSchedule.map(PaymentSchedule::getVersion).orElse(0);
        UUID createdScheduleId = paymentScheduleRepository
                .save(
                        PaymentSchedule.builder()
                                .agreementId(paymentScheduleCreationDto.agreementId())
                                .version(previousVersion + 1)
                                .build()
                )
                .getId();
        return PaymentCreationDto.builder()
                .scheduleId(createdScheduleId)
                .principalAmount(paymentScheduleCreationDto.principalAmount())
                .interest(paymentScheduleCreationDto.interest())
                .term(paymentScheduleCreationDto.term())
                .disbursementDate(paymentScheduleCreationDto.disbursementDate())
                .build();
    }

    public UUID getLatestVersionIdByAgreementId(UUID agreementId) {
        return paymentScheduleRepository
                .findLatestVersionByAgreementId(agreementId)
                .map(PaymentSchedule::getId)
                .orElseThrow();
    }
}
