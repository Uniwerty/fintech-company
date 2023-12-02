package com.academy.fintech.origination.core.client.db.client.repository;

import com.academy.fintech.origination.core.client.db.client.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends CrudRepository<Client, UUID> {
    Optional<Client> findClientByFirstNameAndLastNameAndEmail(String firstName,
                                                              String lastName,
                                                              String email);
}
