package com.academy.fintech.pe.core.schedule.db.payment.repository;

import com.academy.fintech.pe.core.schedule.db.payment.model.Payment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, UUID> {
    Optional<Payment> findPaymentByScheduleIdAndStatusAndPaymentDate(UUID scheduleId,
                                                                     String status,
                                                                     Date paymentDate);

    Optional<Payment> findPaymentByScheduleIdAndPeriodNumber(UUID scheduleId, int periodNumber);
}
