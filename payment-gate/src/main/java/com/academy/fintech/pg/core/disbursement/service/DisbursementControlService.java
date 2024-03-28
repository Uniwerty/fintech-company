package com.academy.fintech.pg.core.disbursement.service;

import com.academy.fintech.pg.core.disbursement.db.disbursement.DisbursementService;
import com.academy.fintech.pg.core.mp.client.MerchantProviderClientService;
import com.academy.fintech.pg.core.origination.client.OriginationClientService;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementConfirmationDto;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementCreationDto;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementDto;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class DisbursementControlService {
    private DisbursementService disbursementService;
    private MerchantProviderClientService merchantProviderClientService;
    private OriginationClientService originationClientService;

    public DisbursementDto createDisbursement(DisbursementCreationDto disbursementCreationDto) {
        return disbursementService.createDisbursement(disbursementCreationDto);
    }

    /**
     * Polls the merchant provider with the fixed delay
     * and checks if processing disbursements are completed.
     * If so, sends a disbursement confirmation to the origination.
     */
    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void checkProcessingDisbursements() {
        for (UUID agreementId : disbursementService.getProcessingAgreementIds()) {
            merchantProviderClientService
                    .getDisbursementResult(agreementId)
                    .subscribe(disbursementResultDto -> {
                        if (disbursementResultDto.isCompleted()) {
                            originationClientService.confirmDisbursement(
                                    DisbursementConfirmationDto.builder()
                                            .agreementId(agreementId)
                                            .disbursementDate(disbursementResultDto.disbursementDate())
                                            .build()
                            );
                            disbursementService.markDisbursementCompleted(agreementId);
                        }
                    });
        }
    }
}
