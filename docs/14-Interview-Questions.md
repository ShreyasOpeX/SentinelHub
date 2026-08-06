# SentinelHub gRPC Interview Questions

## Table of contents

- [Core transport questions](#core-transport-questions)
- [Streaming questions](#streaming-questions)
- [Reliability and security questions](#reliability-and-security-questions)
- [Architecture questions](#architecture-questions)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## Core transport questions

### What is gRPC and why does it exist?

gRPC is a contract-first RPC framework, commonly protobuf over HTTP/2. It exists to give distributed services compact typed calls, generated client/server code, multiplexed connections, and native streaming. It does not remove network failure; callers still need deadlines, retries, and idempotency.

### Why is protobuf efficient and how does it evolve?

It uses numeric field tags and binary values instead of repeatedly transmitting JSON field names and parsing text. Add fields with new tags; never renumber, reuse, or change released field semantics; reserve deleted tags. Efficiency is a benefit, while compatibility is the more important operational property.

### REST versus gRPC?

REST is the stronger default for browser and external developer accessibility. gRPC is usually stronger for trusted internal typed calls and streams. SentinelHub uses REST at the gateway and gRPC behind it so each layer has the protocol suited to its consumers.

## Streaming questions

### Name the four gRPC call types.

Unary is one request/one response. Server streaming is one request/many responses. Client streaming is many requests/one response. Bidirectional streaming is many messages in both directions independently. Select a shape from the data-flow requirement, not merely expected payload size.

### How do you handle a dropped server stream?

Define ordering/sequence, a resume cursor, deduplication behavior, and a reconnect policy. Observe cancellation server-side. A stream only guarantees live delivery during its connection; use Kafka or persisted history for replayable business events.

## Reliability and security questions

### What is a deadline and why propagate it?

It is the caller’s maximum acceptable completion time. Propagation prevents a downstream chain from continuing expensive work after the upstream caller has timed out. A deadline-exceeded command may have succeeded remotely, so retry only with idempotency protection.

### Metadata versus protobuf fields?

Metadata carries transport context such as authorization and tracing. Protobuf carries domain data such as case ID and status. Trust only validated metadata and avoid logging credentials.

### Authentication versus authorization?

Authentication proves an actor/workload identity. Authorization checks whether that identity may execute an operation on a specific resource. A gateway can authenticate users, but Case Service must enforce ownership rules for cases.

## Architecture questions

### Why Kafka after a CaseCreated event rather than a synchronous notification RPC?

Notification delivery should not extend or fail the case-creation request. Kafka lets consumers retry, scale, and replay independently. Use an outbox/atomic publication design so a committed case does not lose its event.

### What is the outbox pattern?

Write the domain state and an event record in one database transaction. A relay publishes unsent records to Kafka and marks them delivered. Consumers remain idempotent because publication may be repeated. It bridges database atomicity and broker delivery without pretending a distributed transaction is free.

## Key Takeaways

- A strong answer names the trade-off and failure mode, not only the feature.
- gRPC calls require deadlines and idempotency; Kafka consumers require schema discipline and idempotency.
- SentinelHub’s layered architecture deliberately separates browser, synchronous ownership, and durable reaction paths.

## Interview Perspective

Expected follow-ups: explain what happens after a timeout; distinguish a live stream from replayable events; describe a safe protobuf field removal; and justify a gateway without making it a business monolith. Common misconception: “exactly once” means no duplicate business effects—design consumers to be idempotent instead.

## Common Mistakes

- Giving performance claims without mentioning workload measurement.
- Saying Kafka guarantees global ordering.
- Claiming TLS alone authorizes an analyst.

## What's Next

Revisit [Architecture](Architecture.md) and trace one case creation end to end.
