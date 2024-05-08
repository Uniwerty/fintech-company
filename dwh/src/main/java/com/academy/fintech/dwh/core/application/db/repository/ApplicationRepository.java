package com.academy.fintech.dwh.core.application.db.repository;

import com.academy.fintech.dwh.core.application.db.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ApplicationRepository extends MongoRepository<Application, UUID> {
}
