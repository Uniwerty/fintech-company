package com.academy.fintech.origination.core.application.db.client.repository;

import com.academy.fintech.origination.core.application.db.client.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends CrudRepository<Client, UUID> {
}
