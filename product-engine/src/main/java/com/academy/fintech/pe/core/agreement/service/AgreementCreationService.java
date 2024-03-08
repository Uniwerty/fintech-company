package com.academy.fintech.pe.core.agreement.service;

import com.academy.fintech.pe.core.agreement.db.agreement.AgreementService;
import com.academy.fintech.pe.core.product.db.product.ProductService;
import com.academy.fintech.pe.public_interface.agreement.dto.AgreementCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgreementCreationService {
    private final AgreementService agreementService;
    private final ProductService productService;

    public UUID create(AgreementCreationDto agreementCreationDto) {
        productService.validateAgreement(agreementCreationDto);
        return agreementService.create(agreementCreationDto);
    }
}
