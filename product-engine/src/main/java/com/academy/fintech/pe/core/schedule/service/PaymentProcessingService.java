package com.academy.fintech.pe.core.schedule.service;

import com.academy.fintech.pe.core.agreement.db.account.AccountService;
import com.academy.fintech.pe.core.agreement.db.agreement.AgreementService;
import com.academy.fintech.pe.core.schedule.db.payment.PaymentService;
import com.academy.fintech.pe.core.schedule.db.schedule.PaymentScheduleService;
import com.academy.fintech.pe.public_interface.payment.dto.PeriodPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PaymentProcessingService {
    private final AgreementService agreementService;
    private final PaymentScheduleService paymentScheduleService;
    private final PaymentService paymentService;
    private final AccountService accountService;

    /**
     * Gets agreements that must be paid on the current date and processes their payments.
     * <p>
     * If the agreement's standard account balance is enough to pay for the period,
     * withdraws the required amount and updates the payment's status to {@code PaymentStatus.PAID}.
     * <p>
     * Otherwise, withdraws from the standard account as much as possible to pay for the period,
     * accrues overdue to the overdue account and updates the payment's status to {@code PaymentStatus.OVERDUE}.
     * <p>
     * Updates the agreement's next payment date to the payment date of the next period.
     */
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    public void processCurrentPeriodPayments() {
        Date currentDate = new Date(System.currentTimeMillis());
        for (UUID agreementId : agreementService.findActiveAgreementsByNextPaymentDate(currentDate)) {
            PeriodPaymentDto periodPaymentDto = getAgreementPeriodPayment(agreementId, currentDate);
            BigDecimal paymentAmount = periodPaymentDto.amount();
            BigDecimal accountBalance = accountService.getAmount(agreementId);
            if (accountBalance.compareTo(paymentAmount) >= 0) {
                accountService.payForPeriodFully(
                        agreementId,
                        paymentAmount,
                        periodPaymentDto.id());
            } else {
                accountService.payForPeriodPartially(
                        agreementId,
                        accountBalance,
                        paymentAmount.subtract(accountBalance),
                        periodPaymentDto.id()
                );
            }
            updateAgreementNextPaymentDate(agreementId, periodPaymentDto);
        }
    }

    private PeriodPaymentDto getAgreementPeriodPayment(UUID agreementId, Date paymentDate) {
        return paymentService.getPeriodPaymentByScheduleIdAndDate(
                paymentScheduleService.getLatestVersionIdByAgreementId(agreementId),
                paymentDate
        );
    }

    private void updateAgreementNextPaymentDate(UUID agreementId, PeriodPaymentDto periodPaymentDto) {
        Date nextPaymentDate = paymentService.getPaymentDateByScheduleIdAndPeriodNumber(
                periodPaymentDto.scheduleId(),
                periodPaymentDto.periodNumber() + 1
        );
        agreementService.setNextPaymentDate(agreementId, nextPaymentDate);
    }
}
