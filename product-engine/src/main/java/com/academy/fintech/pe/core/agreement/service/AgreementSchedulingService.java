package com.academy.fintech.pe.core.agreement.service;

import com.academy.fintech.pe.core.agreement.db.agreement.AgreementService;
import com.academy.fintech.pe.core.schedule.service.ScheduleCreationService;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementActivationDto;
import com.academy.fintech.pe.public_interface.payment.dto.NextPaymentDto;
import com.academy.fintech.pe.public_interface.schedule.dto.PaymentScheduleCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgreementSchedulingService {
    private final AgreementService agreementService;
    private final ScheduleCreationService scheduleCreationService;

    public UUID activate(AgreementActivationDto agreementActivationDto) {
        PaymentScheduleCreationDto paymentScheduleCreationDto = agreementService.activate(agreementActivationDto);
        NextPaymentDto nextPaymentDto = scheduleCreationService.create(paymentScheduleCreationDto);
        agreementService.setNextPaymentDate(agreementActivationDto.id(), nextPaymentDto.date());
        return nextPaymentDto.scheduleId();
    }
}
