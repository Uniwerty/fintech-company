package com.academy.fintech.pe.core.schedule.db.schedule.repository;

import com.academy.fintech.pe.core.schedule.db.schedule.model.PaymentSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentScheduleRepository extends CrudRepository<PaymentSchedule, UUID> {
    @Query(value = """
            SELECT * FROM payment_schedules
            WHERE agreement_id = :agreementId
            AND version = (
            SELECT MAX(version) FROM payment_schedules
            WHERE agreement_id = :agreementId
            )
            """,
            nativeQuery = true)
    Optional<PaymentSchedule> findLatestVersionByAgreementId(@Param("agreementId") UUID agreementId);
}
