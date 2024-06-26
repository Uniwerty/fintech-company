package com.academy.fintech.pe.core.agreement.db.account;

import com.academy.fintech.pe.core.agreement.db.account.model.Account;
import com.academy.fintech.pe.core.agreement.db.account.model.AccountCode;
import com.academy.fintech.pe.core.agreement.db.account.repository.AccountRepository;
import com.academy.fintech.pe.core.schedule.status.PaymentStatus;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public void create(UUID agreementId, AccountCode code) {
        accountRepository.save(
                Account.builder()
                        .agreementId(agreementId)
                        .accountCode(code.name())
                        .build()
        );
    }

    public void addAmount(AgreementPaymentDto agreementPaymentDto) {
        accountRepository.addAmountByAgreementIdAndCode(
                agreementPaymentDto.agreementId(),
                AccountCode.STANDARD.name(),
                agreementPaymentDto.amount()
        );
    }

    public BigDecimal getAmount(UUID agreementId) {
        return accountRepository
                .getAccountByAgreementIdAndAccountCode(
                        agreementId,
                        AccountCode.STANDARD.name()
                )
                .map(Account::getBalance)
                .orElseThrow();
    }

    public void payForPeriodFully(UUID agreementId, BigDecimal paymentAmount, UUID paymentId) {
        accountRepository.payForPeriodFully(
                agreementId,
                paymentAmount,
                paymentId,
                PaymentStatus.PAID.name()
        );
    }

    public void payForPeriodPartially(UUID agreementId,
                                      BigDecimal paymentAmount,
                                      BigDecimal overdueAmount,
                                      UUID paymentId) {
        accountRepository.payForPeriodPartiallyAndAccrueOverdue(
                agreementId,
                paymentAmount,
                overdueAmount,
                paymentId,
                PaymentStatus.OVERDUE.name()
        );
    }
}
