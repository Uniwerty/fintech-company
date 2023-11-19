package com.academy.fintech.pe.core.agreement.db.agreement.model;

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
@Table(name = "agreements")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Agreement {
    @Id
    @Column(name = "agreement_id")
    @GeneratedValue
    private UUID id;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "status")
    private String status;

    @Column(name = "term")
    private int term;

    @Column(name = "interest")
    private BigDecimal interest;

    @Column(name = "principal_amount")
    private BigDecimal principalAmount;

    @Column(name = "disbursement_amount")
    private BigDecimal disbursementAmount;

    @Column(name = "origination_amount")
    private BigDecimal originationAmount;

    @Column(name = "disbursement_date")
    private Date disbursementDate;

    @Column(name = "next_payment_date")
    private Date nextPaymentDate;
}


