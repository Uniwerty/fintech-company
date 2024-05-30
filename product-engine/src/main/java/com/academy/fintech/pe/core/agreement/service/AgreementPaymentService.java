package com.academy.fintech.pe.core.agreement.service;

import com.academy.fintech.pe.core.agreement.db.account.AccountService;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgreementPaymentService {
    private final AccountService accountService;

    public boolean pay(AgreementPaymentDto agreementPaymentDto) {
        accountService.addAmount(agreementPaymentDto);
        return true;
    }
}
