# Client Streaming: Many Inputs, One Outcome

## Table of contents

- [Definition](#definition)
- [Use cases](#use-cases)
- [Correctness and resource control](#correctness-and-resource-control)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## Definition

Client streaming sends many client messages to one server method and returns one response after the client completes. It is ideal when individual records are useful incrementally but the caller needs one aggregate acceptance result.

```text
Import worker -> evidence chunk -> evidence chunk -> complete
Import worker <- ImportSummary (accepted/rejected counts)
```

## Use cases

Use it for bulk evidence imports, telemetry batches, or an analyst uploading many structured observations. It avoids building a giant payload in client memory. Do not use it when each item requires an immediate independent server response; bidirectional streaming or separate unary calls better expresses that contract.

## Correctness and resource control

Define per-item validation, maximum message/stream sizes, transaction semantics, and retry behavior. A reconnect can resend chunks; include an import ID and item IDs to deduplicate. The server should consume incrementally, respect flow control, stop on cancellation, and report partial failures precisely. A final summary should never imply a transaction occurred unless it truly did.

## Key Takeaways

- Client streaming batches transport without requiring one huge request.
- Idempotency and partial-failure rules are central to the API.
- Completion is a protocol event, not a guarantee that every item succeeded.

## Interview Perspective

**Question:** How do you retry an interrupted upload? **Answer:** assign stable import/item identifiers, persist progress or make writes idempotent, then resend safely. **Follow-up:** Why not one large unary payload? It increases memory, timeout, and retry cost.

## Common Mistakes

- Buffering the full stream before processing it.
- Having no size quotas or per-item error model.
- Calling the final summary a success after silently dropping records.

## What's Next

Continue with [Bidirectional Streaming](06-Bidirectional-Streaming.md).
