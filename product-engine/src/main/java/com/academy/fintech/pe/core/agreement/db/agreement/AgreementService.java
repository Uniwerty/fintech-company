package com.academy.fintech.pe.core.agreement.db.agreement;

import com.academy.fintech.pe.public_interface.agreement.dto.AgreementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgreementService {
    private final static String NEW_STATUS = "NEW";
    private final AgreementRepository agreementRepository;

    public UUID create(AgreementDto agreementDto) {
        Agreement createdAgreement = agreementRepository.save(
                Agreement.builder()
                        .clientId(agreementDto.clientId())
                        .productCode(agreementDto.productCode())
                        .status(NEW_STATUS)
                        .term(agreementDto.term())
                        .interest(agreementDto.interest())
                        .disbursementAmount(agreementDto.disbursementAmount())
                        .originationAmount(agreementDto.originationAmount())
                        .build()
        );
        return createdAgreement.getId();
    }
}
