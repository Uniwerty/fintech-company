package com.academy.fintech.origination.core.application.db.outbox.repository;

import com.academy.fintech.origination.core.application.db.outbox.model.ApplicationMessage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface ApplicationMessageRepository extends CrudRepository<ApplicationMessage, UUID> {
    @Modifying
    @Query(value = """
            update application_outbox
            set status = 'PROCESSING'
            where message_id = any(select message_id
                                    from application_outbox
                                    where status = 'NEW'
                                    for update skip locked
                                    limit 1000)
            returning message_id, payload, status
            """,
            nativeQuery = true)
    List<ApplicationMessage> findAllWithNewStatus();

    @Modifying
    @Query(value = """
            update application_outbox
            set status = 'COMPLETED'
            where message_id = :id
            """,
            nativeQuery = true)
    void setCompletedStatusById(@Param("id") UUID id);
}
