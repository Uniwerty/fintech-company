package com.academy.fintech.origination.core.application.db.outbox.repository;

import com.academy.fintech.origination.core.application.db.outbox.model.ApplicationMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationMessageRepository extends CrudRepository<ApplicationMessage, UUID> {
}
