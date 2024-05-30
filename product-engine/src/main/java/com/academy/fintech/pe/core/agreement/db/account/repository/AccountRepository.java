package com.academy.fintech.pe.core.agreement.db.account.repository;

import com.academy.fintech.pe.core.agreement.db.account.model.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
@Transactional
public interface AccountRepository extends CrudRepository<Account, UUID> {
    @Modifying
    @Query(value = """
            update accounts 
            set balance = balance + :amount
            where agreement_id = :agreementId and account_code = :code
            """,
            nativeQuery = true)
    void addAmountByAgreementIdAndCode(@Param("agreementId") UUID agreementId,
                                       @Param("code") String accountCode,
                                       @Param("amount") BigDecimal amount);
}
