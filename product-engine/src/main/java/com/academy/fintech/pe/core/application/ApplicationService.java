package com.academy.fintech.pe.core.application;

import com.academy.fintech.pe.core.agreement.db.agreement.AgreementService;
import com.academy.fintech.pe.core.product.db.product.ProductService;
import com.academy.fintech.pe.core.schedule.calculation.PaymentCalculator;
import com.academy.fintech.pe.public_interface.application.dto.ApplicationDto;
import com.academy.fintech.pe.public_interface.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private static final String DEFAULT_PRODUCT_CODE = "CL-1.0";
    private final ProductService productService;
    private final AgreementService agreementService;

    public BigDecimal getApplicationPeriodPayment(ApplicationDto applicationDto) {
        ProductDto productDto = productService.getProductByCode(DEFAULT_PRODUCT_CODE);
        return PaymentCalculator.calculatePeriodPayment(
                applicationDto.requestedAmount(),
                productDto.maxInterest(),
                productDto.maxTerm()
        );
    }

    public int getClientOverdue(UUID clientId) {
        List<OffsetDateTime> nextPaymentDates = agreementService.getClientNextPaymentDates(clientId);
        OffsetDateTime currentDate = OffsetDateTime.now();
        return (int) nextPaymentDates
                .stream()
                .mapToLong(date -> ChronoUnit.DAYS.between(date, currentDate))
                .filter(overdue -> overdue > 0)
                .max()
                .orElse(0);
    }
}
