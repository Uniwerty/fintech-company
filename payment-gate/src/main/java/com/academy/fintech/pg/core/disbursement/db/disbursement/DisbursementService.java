package com.academy.fintech.pg.core.disbursement.db.disbursement;

import com.academy.fintech.pg.core.disbursement.db.disbursement.model.Disbursement;
import com.academy.fintech.pg.core.disbursement.db.disbursement.model.DisbursementStatus;
import com.academy.fintech.pg.core.disbursement.db.disbursement.repository.DisbursementRepository;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementCreationDto;
import com.academy.fintech.pg.public_interface.payment.dto.DisbursementDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DisbursementService {
    private final DisbursementRepository repository;

    public DisbursementDto createDisbursement(DisbursementCreationDto disbursementCreationDto) {
        Disbursement disbursement = repository.save(
                DisbursementMapper.mapCreationDtoToEntity(disbursementCreationDto)
        );
        return DisbursementMapper.mapEntityToDto(disbursement);
    }

    public List<UUID> getProcessingAgreementIds() {
        return repository
                .findDisbursementsByStatus(DisbursementStatus.PROCESSING)
                .stream()
                .map(Disbursement::getAgreementId)
                .toList();
    }

    public void markDisbursementCompleted(UUID agreementId) {
        repository.updateDisbursementStatusByAgreementId(agreementId, DisbursementStatus.COMPLETED);
    }
}
