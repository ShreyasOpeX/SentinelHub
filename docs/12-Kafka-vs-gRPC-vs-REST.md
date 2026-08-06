# Kafka vs gRPC vs REST: Select by Interaction, Not Preference

## Table of contents

- [Comparison](#comparison)
- [Decision guide](#decision-guide)
- [SentinelHub examples](#sentinelhub-examples)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## Comparison

| Question | REST | gRPC | Kafka |
|---|---|---|---|
| Primary model | HTTP resources | synchronous typed methods/streams | durable asynchronous facts |
| Typical payload | JSON | protobuf | schema-governed event bytes |
| Latency goal | web/API responsiveness | low-latency service interaction | throughput and decoupling |
| Streaming | SSE/WebSocket separately | native | consumers continuously read |
| Browser / external API | strongest | requires gateway/gRPC-Web | not direct |
| Type safety | OpenAPI/tooling | generated schema stubs | event schema tooling |
| Best fit | public surface | internal queries/commands | fan-out, audit, integration |

## Decision guide

First ask whether the caller must wait for an authoritative answer. If yes, choose REST at an external/browser boundary and gRPC internally. Then choose unary for bounded request/response, server streaming for progressive results, client streaming for many chunks/one summary, or bidi for a live session. If the producer is publishing a fact for independent consumers, needs retention/replay, or must not wait for consumers, choose Kafka.

## SentinelHub examples

`POST /cases` is REST because the browser speaks HTTP/JSON. The gateway calls `CreateCase` via unary gRPC because case creation needs a quick canonical response. `StreamCaseHistory` can use server streaming for an internal long history. `CaseCreated` is a Kafka event because notification, audit, and indexing should progress independently; their delivery failure must not undo an accepted case.

## Key Takeaways

- REST is an edge contract; gRPC is a strong internal call contract; Kafka is a durable event contract.
- A request-reply over Kafka is possible but usually more complex than an RPC.
- Eventual consistency is a product and UX choice, not an implementation accident.

## Interview Perspective

**Question:** Can gRPC replace Kafka? **Answer:** no. gRPC streams are connected sessions; Kafka retains records and decouples consumer timing. **Follow-up:** Can Kafka replace gRPC? Only at the cost of asynchronous correlation and eventual consistency.

## Common Mistakes

- Selecting solely on raw throughput benchmarks.
- Calling events commands (“do this”) instead of past facts (“this happened”).
- Coupling a producer transaction to notification availability.

## What's Next

Continue with [Performance](13-Performance.md).
