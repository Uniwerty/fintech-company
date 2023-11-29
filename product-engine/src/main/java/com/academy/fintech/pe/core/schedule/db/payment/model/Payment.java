package com.academy.fintech.pe.core.schedule.db.payment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @Column(name = "payment_id")
    @GeneratedValue
    private UUID id;

    @Column(name = "schedule_id")
    private UUID scheduleId;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "interest_payment")
    private BigDecimal interestPayment;

    @Column(name = "principal_payment")
    private BigDecimal principalPayment;

    @Column(name = "period_number")
    private int periodNumber;
}
