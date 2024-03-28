package com.academy.fintech.mp.disbursement.db.repository;

import com.academy.fintech.mp.disbursement.db.model.Disbursement;
import com.academy.fintech.mp.disbursement.db.model.DisbursementStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface DisbursementRepository extends CrudRepository<Disbursement, UUID> {
    Optional<Disbursement> findDisbursementByAgreementId(UUID agreementId);

    @Modifying
    @Query(value = """
            update disbursements set
            status = :newStatus
            where agreement_id = :agreementId
            """,
            nativeQuery = true)
    void updateStatusByAgreementId(@Param("agreementId") UUID agreementId,
                                   @Param("newStatus") DisbursementStatus newStatus);
}
