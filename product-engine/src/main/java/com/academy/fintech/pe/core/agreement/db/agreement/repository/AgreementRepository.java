package com.academy.fintech.pe.core.agreement.db.agreement.repository;

import com.academy.fintech.pe.core.agreement.db.agreement.model.Agreement;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.UUID;

@Repository
public interface AgreementRepository extends CrudRepository<Agreement, UUID> {
    @Modifying
    @Transactional
    @Query(value = """
            UPDATE agreements SET
            next_payment_date = :date
            WHERE agreement_id = :id
            """,
            nativeQuery = true)
    void updateAgreementNextPaymentDate(@Param("id") UUID agreementId,
                                        @Param("date") Date nextPaymentDate);
}
