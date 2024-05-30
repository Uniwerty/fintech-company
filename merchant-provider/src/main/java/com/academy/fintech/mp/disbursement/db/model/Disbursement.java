package com.academy.fintech.mp.disbursement.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "date")
    private Date date;
}
