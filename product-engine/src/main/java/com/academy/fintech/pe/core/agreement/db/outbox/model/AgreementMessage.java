package com.academy.fintech.pe.core.agreement.db.outbox.model;

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

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agreement_outbox")
public class AgreementMessage {
    @Id
    @Column(name = "message_id")
    @GeneratedValue
    private UUID id;

    @Column(name = "payload")
    private String payload;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AgreementMessageStatus status;
}
