package com.academy.fintech.scoring.core.service;

import com.academy.fintech.scoring.core.pe.client.ProductEngineClientService;
import com.academy.fintech.scoring.public_interface.application.dto.ApplicationCalculationResult;
import com.academy.fintech.scoring.public_interface.application.dto.ApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
@RequiredArgsConstructor
public class ScoringService {
    private static final BigDecimal MAX_SALARY_TO_PAYMENT_RATIO = new BigDecimal(3);
    private static final int SIGNIFICANT_PAYMENT_DELAY_DAYS = 7;
    private final ProductEngineClientService productEngineClientService;

    public boolean getApprovalVerdict(ApplicationDto applicationDto) {
        return countClientScore(applicationDto) > 0;
    }

    private int countClientScore(ApplicationDto applicationDto) {
        ApplicationCalculationResult result = productEngineClientService.getApplicationInfo(applicationDto);
        int score = 0;
        BigDecimal maxPossiblePayment =
                applicationDto.salary().divide(MAX_SALARY_TO_PAYMENT_RATIO, MathContext.DECIMAL64);
        if (result.periodPayment().compareTo(maxPossiblePayment) <= 0) {
            score++;
        }
        if (result.daysOverdue() >= SIGNIFICANT_PAYMENT_DELAY_DAYS) {
            score--;
        } else if (result.daysOverdue() == 0) {
            score++;
        }
        return score;
    }
}
