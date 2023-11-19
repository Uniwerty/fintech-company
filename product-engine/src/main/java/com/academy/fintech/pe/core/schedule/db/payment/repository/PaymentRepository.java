package com.academy.fintech.pe.core.schedule.db.payment.repository;

import com.academy.fintech.pe.core.schedule.db.payment.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, UUID> {
}
