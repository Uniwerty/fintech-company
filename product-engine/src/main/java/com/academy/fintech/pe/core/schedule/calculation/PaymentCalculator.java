package com.academy.fintech.pe.core.schedule.calculation;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class PaymentCalculator {
    private static final BigDecimal PERIODS_PER_YEAR = new BigDecimal(12);
    private static final BigDecimal ONE_HUNDRED_PERCENT = new BigDecimal(100);
    private static final MathContext DECIMAL_CONTEXT = MathContext.DECIMAL64;

    public static BigDecimal calculatePeriodPayment(BigDecimal principalAmount,
                                                    BigDecimal interest,
                                                    int term) {
        BigDecimal periodIncrease = BigDecimal.ONE.add(getPeriodInterest(interest));
        BigDecimal increase = periodIncrease;
        BigDecimal increasesSum = BigDecimal.ONE;
        for (int period = 1; period < term; period++) {
            increasesSum = increasesSum.add(increase);
            increase = increase.multiply(periodIncrease, DECIMAL_CONTEXT);
        }
        return principalAmount
                .multiply(increase, DECIMAL_CONTEXT)
                .divide(increasesSum, DECIMAL_CONTEXT);
    }

    public static List<BigDecimal> calculateInterestPayments(BigDecimal principalAmount,
                                                             BigDecimal interest,
                                                             BigDecimal periodPayment,
                                                             int term) {
        List<BigDecimal> interestPayments = new ArrayList<>(term);
        BigDecimal periodInterest = getPeriodInterest(interest);
        BigDecimal periodIncrease = BigDecimal.ONE.add(periodInterest);
        for (int period = 0; period < term; period++) {
            interestPayments.add(principalAmount.multiply(periodInterest, DECIMAL_CONTEXT));
            principalAmount = principalAmount
                    .multiply(periodIncrease, DECIMAL_CONTEXT)
                    .subtract(periodPayment);
        }
        return interestPayments;
    }

    private static BigDecimal getPeriodInterest(BigDecimal interest) {
        return interest
                .divide(ONE_HUNDRED_PERCENT, DECIMAL_CONTEXT)
                .divide(PERIODS_PER_YEAR, DECIMAL_CONTEXT);
    }
}
