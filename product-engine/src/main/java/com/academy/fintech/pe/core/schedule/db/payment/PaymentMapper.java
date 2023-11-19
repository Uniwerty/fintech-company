package com.academy.fintech.pe.core.schedule.db.payment;

import com.academy.fintech.pe.core.schedule.db.payment.model.Payment;
import com.academy.fintech.pe.public_interface.payment.dto.PaymentDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment mapDtoToEntity(PaymentDto paymentDto) {
        return Payment.builder()
                .scheduleId(paymentDto.scheduleId())
                .status(paymentDto.status())
                .paymentDate(paymentDto.date())
                .interestPayment(paymentDto.interestPayment())
                .principalPayment(paymentDto.principalPayment())
                .periodNumber(paymentDto.periodNumber())
                .build();
    }
}
