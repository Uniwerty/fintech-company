package com.academy.fintech.origination.core.application.db.application.repository;

import com.academy.fintech.origination.core.application.db.application.model.Application;
import com.academy.fintech.origination.core.application.status.ApplicationStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, UUID> {
    Optional<Application> findApplicationByClientIdAndRequestedAmountAndStatus(UUID clientId,
                                                                               BigDecimal requestedAmount,
                                                                               ApplicationStatus status);

    List<Application> findAllByStatus(ApplicationStatus status);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE applications SET
            status = :status
            WHERE application_id = :id
            """,
            nativeQuery = true)
    void updateStatusById(@Param("id") UUID id, @Param("status") ApplicationStatus status);
}
