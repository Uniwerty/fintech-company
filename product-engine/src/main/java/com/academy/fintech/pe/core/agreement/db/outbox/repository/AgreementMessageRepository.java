package com.academy.fintech.pe.core.agreement.db.outbox.repository;

import com.academy.fintech.pe.core.agreement.db.outbox.model.AgreementMessage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AgreementMessageRepository extends CrudRepository<AgreementMessage, UUID> {
    @Modifying
    @Query(value = """
            update agreement_outbox
            set status = 'PROCESSING'
            where message_id = any(select message_id
                                    from agreement_outbox
                                    where status = 'NEW'
                                    for update skip locked
                                    limit 1000)
            returning message_id, payload, status
            """,
            nativeQuery = true)
    List<AgreementMessage> findAllWithNewStatus();

    @Modifying
    @Query(value = """
            update agreement_outbox
            set status = 'COMPLETED'
            where message_id = :id
            """,
            nativeQuery = true)
    void setCompletedStatusById(@Param("id") UUID id);
}
