package com.academy.fintech.pg.core.disbursement.db.disbursement.repository;

import com.academy.fintech.pg.core.disbursement.db.disbursement.model.Disbursement;
import com.academy.fintech.pg.core.disbursement.db.disbursement.model.DisbursementStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface DisbursementRepository extends CrudRepository<Disbursement, UUID> {
    List<Disbursement> findDisbursementsByStatus(DisbursementStatus status);

    @Modifying
    @Query(value = """
            update disbursements set
            status = :newStatus
            where agreement_id = :agreementId
            """,
            nativeQuery = true)
    void updateDisbursementStatusByAgreementId(@Param("agreementId") UUID agreementId,
                                               @Param("newStatus") DisbursementStatus newStatus);
}
