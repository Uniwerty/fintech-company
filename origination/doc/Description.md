# Origination
This service
- creates, stores and cancels applications;
- creates and stores clients.

## Technology used
- Service interaction: `gRPC`

ProtoBuf interface specification is present at
[src/main/proto/ApplicationServiceV1.proto](../src/main/proto/ApplicationServiceV1.proto)

The service receives application creation and cancellation requests, responds to them.

- Database Framework: `PostgreSQL`

Database schema is present:

as sql files at [src/main/resources/db/changelog](../src/main/resources/db/changelog)

as picture at [DatabaseSchema.png](DatabaseSchema.png)

- Database Migration Framework: `Liquibase`

Migration changelog is present at
[src/main/resources/db/db.changelog-master.yaml](../src/main/resources/db/db.changelog-master.yaml)