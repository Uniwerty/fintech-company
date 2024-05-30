package com.academy.fintech.origination.core.application.db.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private UUID id;

    @Column(name = "client_id")
    @JsonProperty("client_id")
    private UUID clientId;

    @Column(name = "requested_amount")
    @JsonProperty("requested_amount")
    private BigDecimal requestedAmount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private ApplicationStatus status;

    @Column(name = "creation_date")
    @JsonProperty("creation_date")
    private Date creationDate;

    @Column(name = "last_update_date")
    @JsonProperty("last_update_date")
    private Date lastUpdateDate;
}
