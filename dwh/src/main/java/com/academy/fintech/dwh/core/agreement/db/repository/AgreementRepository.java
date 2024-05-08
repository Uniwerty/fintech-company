package com.academy.fintech.dwh.core.agreement.db.repository;

import com.academy.fintech.dwh.core.agreement.db.model.Agreement;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface AgreementRepository extends MongoRepository<Agreement, UUID> {
}
