package com.academy.fintech.origination.core.application.db.application.repository;

import com.academy.fintech.origination.core.application.db.application.model.Application;
import com.academy.fintech.origination.core.application.db.application.model.ApplicationStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface ApplicationRepository extends CrudRepository<Application, UUID> {
    Optional<Application> findApplicationByClientIdAndRequestedAmountAndStatus(UUID clientId,
                                                                               BigDecimal requestedAmount,
                                                                               ApplicationStatus status);

    List<Application> findAllByStatus(ApplicationStatus status);
}
