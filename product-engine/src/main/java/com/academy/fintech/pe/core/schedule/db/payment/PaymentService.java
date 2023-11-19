package com.academy.fintech.pe.core.schedule.db.payment;

import com.academy.fintech.pe.core.schedule.db.payment.repository.PaymentRepository;
import com.academy.fintech.pe.public_interface.payment.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public void saveAll(List<PaymentDto> paymentDtos) {
        paymentRepository.saveAll(
                paymentDtos.stream()
                        .map(paymentMapper::mapDtoToEntity)
                        .toList()
        );
    }
}
