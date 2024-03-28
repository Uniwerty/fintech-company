package com.academy.fintech.mp.disbursement.db;

import com.academy.fintech.mp.disbursement.db.model.Disbursement;
import com.academy.fintech.mp.disbursement.db.model.DisbursementStatus;
import com.academy.fintech.mp.disbursement.db.repository.DisbursementRepository;
import com.academy.fintech.mp.public_interface.dto.disbursement.DisbursementCreationDto;
import com.academy.fintech.mp.public_interface.dto.disbursement.DisbursementResultDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DisbursementService {
    private final DisbursementRepository repository;

    public void createDisbursement(DisbursementCreationDto creationDto) {
        repository.save(DisbursementMapper.mapCreationDtoToEntity(creationDto));
    }

    public void updateDisbursementStatusByAgreementId(UUID agreementId) {
        repository.updateStatusByAgreementId(agreementId, DisbursementStatus.COMPLETED);
    }

    public DisbursementResultDto getDisbursementResult(UUID agreementId) {
        Optional<Disbursement> optionalDisbursement = repository.findDisbursementByAgreementId(agreementId);
        if (optionalDisbursement.isEmpty()) {
            return DisbursementResultDto.builder()
                    .isCompleted(false)
                    .build();
        } else {
            return DisbursementResultDto.builder()
                    .isCompleted(true)
                    .date(optionalDisbursement.get().getDate())
                    .build();
        }
    }
}
