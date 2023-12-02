package com.academy.fintech.origination.core.application.db.application.repository;

import com.academy.fintech.origination.core.application.db.application.model.Application;
import com.academy.fintech.origination.core.application.status.ApplicationStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, UUID> {
    Optional<Application> findApplicationByClientIdAndRequestedAmountAndStatus(UUID clientId,
                                                                               BigDecimal requestedAmount,
                                                                               ApplicationStatus status);
}
