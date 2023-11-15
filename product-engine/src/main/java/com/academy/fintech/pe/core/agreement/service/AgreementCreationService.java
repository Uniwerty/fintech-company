package com.academy.fintech.pe.core.agreement.service;

import com.academy.fintech.pe.core.agreement.db.agreement.AgreementService;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgreementCreationService {
    private final AgreementService agreementService;

    public UUID create(AgreementDto agreementDto) {
        return agreementService.create(agreementDto);
    }
}
