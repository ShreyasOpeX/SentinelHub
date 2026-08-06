# Bidirectional Streaming: A Live Conversation

## Table of contents

- [Definition](#definition)
- [Appropriate use](#appropriate-use)
- [Session design](#session-design)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## Definition

Bidirectional streaming allows client and server to send any number of messages independently on the same RPC. Neither side must wait for the other to finish. It models an active session, not a sequence of nested request-response calls.

```text
Analyst client <---- presence / case updates ----> Collaboration Service
               ---- cursor / comment actions --->
```

## Appropriate use

Use it for live collaboration, interactive control planes, and continuous telemetry where acknowledgements or server pushes occur while input continues. Do not use it as a universal replacement for unary calls: connection lifecycle, reconnect, load balancing, authorization refresh, and debugging are all more complex.

## Session design

Authenticate at stream setup, include a session and resume cursor, enforce heartbeats/idle limits, sequence meaningful messages, and make handlers concurrency-safe. Decide whether each update is ephemeral (presence) or must become a durable event (comment). Persist durable changes separately and publish them to Kafka; a dropped stream must not erase a committed business fact.

## Key Takeaways

- Bidirectional means independent directionality, not guaranteed persistence.
- A reconnect/resume contract is required in production.
- Separate transient session state from durable domain events.

## Interview Perspective

**Question:** Why not Kafka for live cursor updates? **Answer:** cursors are low-value, transient and interactive; a live stream avoids durable-log overhead. **Follow-up:** What happens on reconnect? Reauthenticate and resubscribe from a cursor/snapshot.

## Common Mistakes

- One thread writing to a response observer without serialization discipline.
- No heartbeat, maximum lifetime, or cleanup policy.
- Treating a connection as proof that a message was durably stored.

## What's Next

Continue with [Metadata](07-Metadata.md).
