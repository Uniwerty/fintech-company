package com.academy.fintech.pe.core.schedule.db.payment;

import com.academy.fintech.pe.core.schedule.db.payment.model.Payment;
import com.academy.fintech.pe.core.schedule.db.payment.repository.PaymentRepository;
import com.academy.fintech.pe.core.schedule.status.PaymentStatus;
import com.academy.fintech.pe.public_interface.payment.dto.PaymentDto;
import com.academy.fintech.pe.public_interface.payment.dto.PeriodPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public void saveAll(List<PaymentDto> paymentDtos) {
        paymentRepository.saveAll(
                paymentDtos.stream()
                        .map(PaymentMapper::mapDtoToEntity)
                        .toList()
        );
    }

    public PeriodPaymentDto getPeriodPaymentByScheduleIdAndDate(UUID scheduleId, Date paymentDate) {
        return paymentRepository
                .findPaymentByScheduleIdAndStatusAndPaymentDate(
                        scheduleId,
                        PaymentStatus.FUTURE.name(),
                        paymentDate
                )
                .map(PaymentMapper::mapEntityToPeriodPaymentDto)
                .orElseThrow();
    }

    public Date getPaymentDateByScheduleIdAndPeriodNumber(UUID scheduleId, int periodNumber) {
        return paymentRepository
                .findPaymentByScheduleIdAndPeriodNumber(scheduleId, periodNumber)
                .map(Payment::getPaymentDate)
                .orElse(null);
    }
}
