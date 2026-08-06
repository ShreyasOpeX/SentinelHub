# SentinelHub Architecture

## Table of contents

- [System context](#system-context)
- [Service ownership](#service-ownership)
- [Communication paths](#communication-paths)
- [Case creation sequence](#case-creation-sequence)
- [Reliability and operational rules](#reliability-and-operational-rules)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## System context

SentinelHub separates the web edge, synchronous domain ownership, and asynchronous reactions. This avoids exposing internal service topology to a browser while keeping state-change side effects decoupled from the interactive request.

```text
                         +------------------------+
                         | Browser / API Consumer |
                         +-----------+------------+
                                     REST/JSON
                                       |
                         +-------------v----------+
                         | Gateway Service         |
                         | auth, HTTP, validation  |
                         +--+-----+-----+-----+----+
                            |     |     |     |  gRPC/protobuf
               +------------+     |     |     +-------------+
               v                  v     v                   v
        +-------------+  +-------------+ +-------------+ +----------------+
        | Case Service|  | Customer    | | History     | | Collaboration  |
        | case owner  |  | Service     | | Service     | | Service        |
        +------+------+  +-------------+ +-------------+ +----------------+
               |
               | CaseCreated / CaseUpdated (Kafka)
               v
        +-------------------+       +----------------------+
        | Notification      |       | Other future consumers|
        | Service           |       | audit, search, BI     |
        +-------------------+       +----------------------+
```

## Service ownership

| Component | Owns | Synchronous responsibility | Asynchronous responsibility |
|---|---|---|---|
| Gateway | external API adaptation | authenticate/validate/map REST | never own domain events |
| Case | case lifecycle | create, read, assign, update status | publish case facts |
| Customer | customer facts | lookup/enrichment | publish customer facts when needed |
| History | immutable activity view | query/stream history | consume facts to record history |
| Collaboration | live session state | presence/comments/live updates | publish durable collaboration facts |
| Notification | delivery attempts | no inline caller dependency | consume events and retry delivery |

The repository currently contains the Case Service protobuf contract and module scaffold. This diagram is the intended architecture for implementation; it does not claim all runtime integrations are already committed.

## Communication paths

### Browser to gateway: REST

Browsers natively understand HTTP/JSON, cookies, CORS, caching, and conventional error codes. The gateway provides a stable public API even if internal protobuf methods evolve. It converts external DTOs to internal contracts and must not leak a service’s storage model.

### Gateway to domain services: gRPC

Internal callers benefit from generated type-safe stubs, protobuf efficiency, HTTP/2 connection reuse, explicit deadlines, and streaming. Every RPC should use a deadline, carry validated identity/trace metadata, and return a meaningful status. The caller may compose reads but should avoid a fan-out chain that makes availability depend on every service.

### Services to notification: Kafka

A successful state transition publishes a past-tense fact such as `CaseCreated`. Kafka permits notification retries and future consumers without blocking the original user. Use partition keys such as `case_id` when order for one case matters. Consumers must be idempotent because records can be delivered again during failures/rebalances.

## Case creation sequence

```text
Browser       Gateway        Case Service             Kafka         Notification
   | POST /cases |               |                     |                |
   |------------>| validate/auth |                     |                |
   |             | CreateCase + deadline              |                |
   |             |-------------->| validate, persist  |                |
   |             |<--------------| CaseResponse       |                |
   |<------------| 201 / JSON    |                     |                |
   |             |               | outbox relay ------>| CaseCreated    |
   |             |               |                     |--------------->|
   |             |               |                     |      send/retry |
```

The outbox relay is intentionally after persistence but atomically recorded with it. The user response does not wait for notification. If the relay or consumer fails, the case remains correct and the event is retried; monitoring detects lag and dead-letter policy handles poison messages.

## Reliability and operational rules

- Each service owns its datastore; integrate through contracts/events, never cross-service table writes.
- Define protobuf and event compatibility rules before independent deployment.
- Propagate finite deadlines; make state-changing commands idempotent with a request key.
- Authenticate service hops, authorize at resource owners, and redact credentials in logs.
- Trace one request across gateway, gRPC calls, outbox publication, and Kafka consumption using correlation IDs.
- Alert on gRPC status/latency, connection health, Kafka consumer lag, outbox backlog, and notification failures.

## Key Takeaways

- The gateway is a protocol and security boundary, while domain services own decisions and data.
- gRPC handles immediate service interaction; Kafka handles durable independent reactions.
- The outbox plus idempotent consumers makes cross-system failure behavior explicit.

## Interview Perspective

**Question:** Why not have Notification Service call Case Service periodically? **Answer:** polling couples freshness to schedule and loses the natural event boundary; a durable case event provides prompt, replayable, independently scalable integration. **Follow-up:** What prevents duplicate messages? Consumers use an idempotency key/event ID; brokers cannot replace business-level idempotency.

## Common Mistakes

- Sharing a database to avoid defining a contract.
- Publishing an event only after a separate DB commit without an outbox.
- Making browser requests wait on every side effect.

## What's Next

Start the learning sequence at [Project Overview](00-Project-Overview.md), then read [Why gRPC](01-Why-gRPC.md).
