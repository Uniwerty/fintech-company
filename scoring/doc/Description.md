# Scoring

This service scores applications according to client's salary and credit history.

## Technology used

- Service interaction: `gRPC`

ProtoBuf interface specifications:

- origination interaction interface:
  [ScoringServiceV1.proto](../src/main/proto/origination/ScoringServiceV1.proto)
- product engine interaction interface:
  [ApplicationCalculationServiceV1.proto](../src/main/proto/pe/ApplicationCalculationServiceV1.proto)

This service receives scoring requests, responds to them.