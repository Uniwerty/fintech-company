package com.academy.fintech.pe.core.agreement.db.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "product_code")
    String productCode;

    @Column(name = "min_term")
    int minTerm;

    @Column(name = "max_term")
    int maxTerm;

    @Column(name = "min_interest")
    BigDecimal minInterest;

    @Column(name = "max_interest")
    BigDecimal maxInterest;

    @Column(name = "min_disbursement_amount")
    BigDecimal minDisbursementAmount;

    @Column(name = "max_disbursement_amount")
    BigDecimal maxDisbursementAmount;

    @Column(name = "min_origination_amount")
    BigDecimal minOriginationAmount;

    @Column(name = "max_origination_amount")
    BigDecimal maxOriginationAmount;
}
