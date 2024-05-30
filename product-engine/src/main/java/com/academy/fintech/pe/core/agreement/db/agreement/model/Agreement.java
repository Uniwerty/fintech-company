package com.academy.fintech.pe.core.agreement.db.agreement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("agreement_id")
    private UUID id;

    @Column(name = "product_code")
    @JsonProperty("product_code")
    private String productCode;

    @Column(name = "client_id")
    @JsonProperty("client_id")
    private UUID clientId;

    @Column(name = "status")
    @JsonProperty("status")
    private String status;

    @Column(name = "term")
    @JsonProperty("term")
    private int term;

    @Column(name = "interest")
    @JsonProperty("interest")
    private BigDecimal interest;

    @Column(name = "principal_amount")
    @JsonProperty("principal_amount")
    private BigDecimal principalAmount;

    @Column(name = "disbursement_amount")
    @JsonProperty("disbursement_amount")
    private BigDecimal disbursementAmount;

    @Column(name = "origination_amount")
    @JsonProperty("origination_amount")
    private BigDecimal originationAmount;

    @Column(name = "disbursement_date")
    @JsonProperty("disbursement_date")
    private Date disbursementDate;

    @Column(name = "next_payment_date")
    @JsonProperty("next_payment_date")
    private Date nextPaymentDate;
}


