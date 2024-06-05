package com.academy.fintech.dwh.core.application.db.model;

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
public class Application {
    @Id
    private UUID id;
    private UUID clientId;
    BigDecimal requestedAmount;
    String status;
    Date creationDate;
    Date lastUpdateDate;
}

