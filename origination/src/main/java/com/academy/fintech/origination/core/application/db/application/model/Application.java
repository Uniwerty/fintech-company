package com.academy.fintech.origination.core.application.db.application.model;

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
@Table(name = "applications")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @Id
    @Column(name = "application_id")
    @GeneratedValue
    private UUID id;

    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "requested_amount")
    private BigDecimal requestedAmount;

    @Column(name = "status")
    private String status;

    @Column(name = "creation_date")
    private Date creationDate;
}
