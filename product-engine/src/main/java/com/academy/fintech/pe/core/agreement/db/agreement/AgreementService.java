package com.academy.fintech.pe.core.agreement.db.agreement;

import com.academy.fintech.pe.public_interface.agreement.dto.AgreementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AgreementService {
    private final static String NEW_STATUS = "NEW";
    private final AgreementRepository agreementRepository;

    public String create(AgreementDto agreementDto) {
        BigDecimal principalAmount =
                agreementDto.disbursementAmount().add(agreementDto.originationAmount());
        Agreement createdAgreement = agreementRepository.save(
                Agreement.builder()
                        .clientId(agreementDto.clientId())
                        .productCode(agreementDto.productCode())
                        .status(NEW_STATUS)
                        .loanTerm(agreementDto.loanTerm())
                        .interest(agreementDto.interest())
                        .originationAmount(agreementDto.originationAmount())
                        .principalAmount(principalAmount)
                        .build()
        );
        return createdAgreement.getId();
    }
}
