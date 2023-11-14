package com.academy.fintech.pe.core.agreement.db.agreement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "agreements")
@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class Agreement {
    @Id
    @Column(name = "agreement_id")
    private String id;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "client_id")
    private String clientId;

    private String status;

    @Column(name = "loan_term")
    private int loanTerm;

    private BigDecimal interest;

    @Column(name = "principal_amount")
    private BigDecimal principalAmount;

    @Column(name = "origination_amount")
    private BigDecimal originationAmount;

    @Column(name = "disbursement_date")
    private Date disbursementDate;

    @Column(name = "next_payment_date")
    private Date nextPaymentDate;
}


