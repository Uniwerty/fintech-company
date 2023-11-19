package com.academy.fintech.pe.core.schedule.service;

import com.academy.fintech.pe.core.schedule.calculation.PaymentCalculator;
import com.academy.fintech.pe.core.schedule.db.payment.PaymentService;
import com.academy.fintech.pe.core.schedule.status.PaymentStatus;
import com.academy.fintech.pe.public_interface.payment.dto.NextPaymentDto;
import com.academy.fintech.pe.public_interface.payment.dto.PaymentCreationDto;
import com.academy.fintech.pe.public_interface.payment.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentCreationService {
    private final PaymentService paymentService;
    private final PaymentCalculator paymentCalculator;

    public NextPaymentDto create(PaymentCreationDto paymentCreationDto) {
        List<PaymentDto> paymentDtos = getPaymentDtosList(paymentCreationDto);
        paymentService.saveAll(paymentDtos);
        return NextPaymentDto.builder()
                .scheduleId(paymentCreationDto.scheduleId())
                .date(paymentDtos.get(0).date())
                .build();
    }

    private List<PaymentDto> getPaymentDtosList(PaymentCreationDto paymentCreationDto) {
        BigDecimal periodPayment = paymentCalculator.calculatePeriodPayment(
                paymentCreationDto.principalAmount(),
                paymentCreationDto.interest(),
                paymentCreationDto.term()
        );
        List<BigDecimal> interestPayments = paymentCalculator.calculateInterestPayments(
                paymentCreationDto.principalAmount(),
                paymentCreationDto.interest(),
                periodPayment,
                paymentCreationDto.term()
        );
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(paymentCreationDto.disbursementDate());
        List<PaymentDto> payments = new ArrayList<>(paymentCreationDto.term());
        for (int period = 1; period <= paymentCreationDto.term(); period++) {
            BigDecimal interestPayment = interestPayments.get(period);
            calendar.add(Calendar.MONTH, 1);
            payments.add(
                    PaymentDto.builder()
                            .scheduleId(paymentCreationDto.scheduleId())
                            .status(PaymentStatus.FUTURE.name())
                            .date(new Date(calendar.getTime().getTime()))
                            .interestPayment(interestPayment)
                            .principalPayment(periodPayment.subtract(interestPayment))
                            .periodNumber(period)
                            .build()
            );
        }
        return payments;
    }
}
