package com.academy.fintech.dwh.core.agreement.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "agreements")
public class Agreement {
    @Id
    private UUID id;
    private String productCode;
    private UUID clientId;
    private String status;
    private int term;
    private BigDecimal interest;
    private BigDecimal principalAmount;
    private BigDecimal disbursementAmount;
    private BigDecimal originationAmount;
    private Date disbursementDate;
    private Date nextPaymentDate;
}
