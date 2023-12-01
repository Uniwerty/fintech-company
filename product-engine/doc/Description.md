# Product Engine

This service

- creates, activates and stores agreements;
- creates and stores payment schedules;
- creates, calculates and stores payments;
- calculates and sends client information for scoring.

## Technology used

- Service interaction: `gRPC`

ProtoBuf interface specifications:

- payment gate interaction interface:
  [AgreementServiceV1.proto](../src/main/proto/pg/AgreementServiceV1.proto)
- scoring interaction interface:
  [ApplicationCalculationServiceV1.proto](../src/main/proto/scoring/ApplicationCalculationServiceV1.proto)

The service receives agreement creation and activation requests, responds to them.

- Database Framework: `PostgreSQL`

Database schema is present:

as sql files at [src/main/resources/db/changelog](../src/main/resources/db/changelog)

as picture at [DatabaseSchema.png](DatabaseSchema.png)

- Database Migration Framework: `Liquibase`

Migration changelog is present at
[src/main/resources/db/db.changelog-master.yaml](../src/main/resources/db/db.changelog-master.yaml)