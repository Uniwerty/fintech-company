package com.academy.fintech.pe.core.schedule.db.schedule.model;

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

import java.util.UUID;

@Entity
@Table(name = "payment_schedules")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSchedule {
    @Id
    @Column(name = "schedule_id")
    @GeneratedValue
    private UUID id;

    @Column(name = "agreement_id")
    private UUID agreementId;

    @Column(name = "version")
    private int version;
}
