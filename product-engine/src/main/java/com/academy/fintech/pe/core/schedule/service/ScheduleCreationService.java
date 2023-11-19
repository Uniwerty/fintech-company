package com.academy.fintech.pe.core.schedule.service;

import com.academy.fintech.pe.core.schedule.db.schedule.PaymentScheduleService;
import com.academy.fintech.pe.public_interface.payment.dto.NextPaymentDto;
import com.academy.fintech.pe.public_interface.payment.dto.PaymentCreationDto;
import com.academy.fintech.pe.public_interface.schedule.dto.PaymentScheduleCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleCreationService {
    private final PaymentScheduleService paymentScheduleService;
    private final PaymentCreationService paymentCreationService;

    public NextPaymentDto create(PaymentScheduleCreationDto paymentScheduleCreationDto) {
        PaymentCreationDto paymentCreationDto = paymentScheduleService.create(paymentScheduleCreationDto);
        return paymentCreationService.create(paymentCreationDto);
    }
}
