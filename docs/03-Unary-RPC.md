# Unary RPC: One Request, One Response

## Table of contents

- [Definition and lifecycle](#definition-and-lifecycle)
- [When to use unary RPC](#when-to-use-unary-rpc)
- [Designing SentinelHub commands](#designing-sentinelhub-commands)
- [Production practice](#production-practice)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## Definition and lifecycle

Unary RPC is the familiar request-response shape: a client sends exactly one protobuf message and receives exactly one message or terminal error. gRPC maps the method to an HTTP/2 stream, serializes the request, invokes the handler, and closes the stream after the response.

```text
Gateway -- CreateCaseRequest --> Case Service
Gateway <-- CaseResponse ------ Case Service
```

## When to use unary RPC

Use it for bounded, interactive work whose caller needs an immediate outcome: get a case, validate a customer, create a case, or assign an analyst. It is not a substitute for a job queue: if processing can take minutes or must survive caller disconnection, accept a command and process it asynchronously.

## Designing SentinelHub commands

The present `CaseService` uses unary methods appropriately. `CreateCase` should validate input, enforce authorization, persist atomically, and return the canonical state. A production create command needs an idempotency key so a gateway retry does not create two cases. `GetCase` should return `NOT_FOUND`, not an empty success response.

## Production practice

Set a deadline, map domain failures to stable status codes, propagate trace and identity metadata, and record latency/error metrics. Retry only idempotent calls or calls protected by an idempotency key; a timeout means the server may still have completed the work.

## Key Takeaways

- Unary is ideal for small, immediate command/query interactions.
- A deadline and error taxonomy are part of the API contract.
- Idempotency makes retry safe; retries alone do not.

## Interview Perspective

**Question:** Is unary RPC always faster than REST? **Answer:** no; protobuf and HTTP/2 can reduce overhead, but network, database, fan-out, and payload shape dominate end-to-end latency. **Follow-up:** What status after a missing case? `NOT_FOUND`.

## Common Mistakes

- Returning a business failure in a successful empty response.
- Retrying non-idempotent creates blindly.
- Using unary to transfer an unbounded result set.

## What's Next

Continue with [Server Streaming](04-Server-Streaming.md).
