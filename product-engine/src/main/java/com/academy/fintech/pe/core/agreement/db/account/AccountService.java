package com.academy.fintech.pe.core.agreement.db.account;

import com.academy.fintech.pe.core.agreement.db.account.model.Account;
import com.academy.fintech.pe.core.agreement.db.account.model.AccountCode;
import com.academy.fintech.pe.core.agreement.db.account.repository.AccountRepository;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        accountRepository.addAmountByAgreementId(
                agreementPaymentDto.agreementId(),
                agreementPaymentDto.amount()
        );
    }
}
