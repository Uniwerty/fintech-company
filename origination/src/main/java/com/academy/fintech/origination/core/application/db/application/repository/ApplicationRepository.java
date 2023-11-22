package com.academy.fintech.origination.core.application.db.application.repository;

import com.academy.fintech.origination.core.application.db.application.model.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, UUID> {
}
