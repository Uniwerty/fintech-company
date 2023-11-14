package com.academy.fintech.pe.core.agreement.db.agreement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepository extends CrudRepository<Agreement, String> {
}
