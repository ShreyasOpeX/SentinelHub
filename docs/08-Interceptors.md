# gRPC Interceptors: The Transport Policy Layer

## Table of contents

- [Definition](#definition)
- [Client and server responsibilities](#client-and-server-responsibilities)
- [Ordering and safety](#ordering-and-safety)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## Definition

Interceptors wrap gRPC calls before application handlers or client transport. They centralize cross-cutting policy: authentication, tracing, metrics, logging, metadata injection, and exception/status translation. They should not become hidden business workflow engines.

## Client and server responsibilities

A client interceptor can attach trace headers and credentials, measure duration, and apply safe policy. A server interceptor can authenticate, create a request context, start a span, record status, and reject unauthenticated calls before business code. Business authorization still needs domain-aware checks near the operation.

## Ordering and safety

Order determines visibility: tracing generally wraps broad work; authentication must run before handlers; logging must redact secrets. Interceptors are concurrent and must not store per-call state in shared mutable fields. Keep synchronous work small: blocking token introspection on every call can exhaust gRPC event-loop/worker capacity.

## Key Takeaways

- Interceptors enforce reusable transport policy.
- Their ordering and redaction policy are production behavior.
- Authentication establishes identity; authorization decides permission.

## Interview Perspective

**Question:** Where should tracing live? **Answer:** interceptors provide consistent span/context propagation, while services add domain spans where useful. **Follow-up:** Why not authorization only in an interceptor? Fine-grained ownership rules often require the request and domain state.

## Common Mistakes

- Logging raw authorization metadata.
- Catching every exception and returning `UNKNOWN`.
- Putting database-heavy permission checks in a global interceptor.

## What's Next

Continue with [Deadlines](09-Deadlines.md).
