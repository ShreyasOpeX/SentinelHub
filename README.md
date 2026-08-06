# SentinelHub — gRPC Learning Platform

SentinelHub is a Java 21/Spring Boot multi-module reference platform for learning how a real-time incident-investigation system can combine REST at the browser edge, gRPC between services, and Kafka for durable asynchronous events. The repository is deliberately organized as a backend engineering handbook as well as a codebase.

## Architecture

```text
Browser / API consumer
        | REST/JSON
        v
 Gateway Service (edge concerns: auth, validation, HTTP)
        | gRPC / Protocol Buffers over HTTP/2
        +--> Case Service ---------+
        +--> Customer Service -----+--> Kafka topics --> Notification Service
        +--> History Service ------+
        +--> Collaboration Service-+
```

REST is the public, browser-friendly boundary; gRPC provides typed low-latency request and streaming paths internally; Kafka preserves business events for independently consuming services. See [Architecture](docs/Architecture.md) for boundaries and failure semantics.

## Repository structure

```text
proto/                    Shared protobuf schemas and generated-stub build module
gateway-service/          REST-facing API gateway (target edge adapter)
case-service/             Case lifecycle owner; CaseService contract exists today
customer-service/         Customer lookup and enrichment owner
history-service/          Immutable case activity/history owner
collaboration-service/    Live analyst collaboration owner
notification-service/     Event-driven notification consumer
docs/                     Learning sequence and engineering handbook
```

## Technology stack

- Java 21 and Maven multi-module build
- Spring Boot 3.4.2
- gRPC Java 1.68.2 with `grpc-spring-boot-starter`
- Protocol Buffers 3.25.5 and HTTP/2 transport
- Apache Kafka as the planned asynchronous event backbone

## Services and communication

The gateway translates browser REST/JSON into internal gRPC calls. `CaseService` currently defines create, read, status-update, and assignment unary RPCs in `proto/src/main/proto/case_service.proto`. Customer, history, and collaboration services are the intended bounded contexts; they should own their data and publish domain events rather than share databases. Notification consumes Kafka events because delivery must be decoupled from the user-facing request.

## Learning roadmap

1. Understand the platform and its boundaries: [Project Overview](docs/00-Project-Overview.md), [Architecture](docs/Architecture.md).
2. Learn contracts and call shapes: [Why gRPC](docs/01-Why-gRPC.md), [Protocol Buffers](docs/02-Protocol-Buffers.md), [Unary RPC](docs/03-Unary-RPC.md), then streaming chapters 04–06.
3. Make calls production-ready: metadata, interceptors, deadlines, authentication, and versioning.
4. Select the right transport and operate it: comparison, performance, and interview practice.

## Documentation index

| # | Guide |
|---|---|
| 00 | [Project Overview](docs/00-Project-Overview.md) |
| 01 | [Why gRPC](docs/01-Why-gRPC.md) |
| 02 | [Protocol Buffers](docs/02-Protocol-Buffers.md) |
| 03 | [Unary RPC](docs/03-Unary-RPC.md) |
| 04 | [Server Streaming](docs/04-Server-Streaming.md) |
| 05 | [Client Streaming](docs/05-Client-Streaming.md) |
| 06 | [Bidirectional Streaming](docs/06-Bidirectional-Streaming.md) |
| 07 | [Metadata](docs/07-Metadata.md) |
| 08 | [Interceptors](docs/08-Interceptors.md) |
| 09 | [Deadlines](docs/09-Deadlines.md) |
| 10 | [Authentication](docs/10-Authentication.md) |
| 11 | [Versioning](docs/11-Versioning.md) |
| 12 | [Kafka vs gRPC vs REST](docs/12-Kafka-vs-gRPC-vs-REST.md) |
| 13 | [Performance](docs/13-Performance.md) |
| 14 | [Interview Questions](docs/14-Interview-Questions.md) |
| — | [Architecture](docs/Architecture.md) |

## Development roadmap

- Implement the gateway-to-CaseService adapter and generated-stub integration.
- Add protobuf contracts for the remaining bounded contexts.
- Add validation, identity propagation, deadlines, telemetry, and contract tests.
- Publish versioned case events through an outbox and consume them in history and notification services.
- Load-test unary and streaming paths; tune from measurements, not assumptions.

## Learning objectives

After completing the handbook, a developer should be able to model backward-compatible protobuf APIs, select a gRPC call shape, propagate identity safely, bound latency with deadlines, decide when an event log is more appropriate than RPC, and explain the resulting trade-offs in a production design review.
