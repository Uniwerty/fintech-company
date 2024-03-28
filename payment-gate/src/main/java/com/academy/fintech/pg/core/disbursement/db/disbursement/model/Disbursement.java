package com.academy.fintech.pg.core.disbursement.db.disbursement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "disbursements")
public class Disbursement {
    @Id
    @Column(name = "agreement_id")
    private UUID agreementId;

    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DisbursementStatus status;

    @Column(name = "amount")
    private BigDecimal amount;
}
