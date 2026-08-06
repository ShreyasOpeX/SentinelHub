# Server Streaming: Progressive Results

## Table of contents

- [Definition](#definition)
- [When it helps](#when-it-helps)
- [Flow control and cancellation](#flow-control-and-cancellation)
- [SentinelHub application](#sentinelhub-application)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## Definition

In server streaming, the client sends one request and the server sends zero or more response messages before completing. It lets a consumer begin useful work before the producer has assembled the entire result.

```text
Client -- ListCaseHistory(caseId) --> History Service
Client <-- HistoryEntry #1 ---------
Client <-- HistoryEntry #2 ---------
Client <-- complete ----------------
```

## When it helps

Use it for ordered histories, investigation search results, live alerts, and large exports where progressive delivery improves time-to-first-result and avoids holding a giant list in memory. Do not use it merely to hide an expensive unbounded query; provide filters, paging/cursors, retention limits, and a cancellation policy.

## Flow control and cancellation

HTTP/2/gRPC flow control limits how much a sender can place in flight, so a slow client creates backpressure. The server must still observe cancellation and stop database reads or background work. Each streamed item should be independently meaningful; the terminal status tells the client whether the sequence completed successfully.

## SentinelHub application

`HistoryService.StreamCaseHistory` is a natural target contract: request a case ID, cursor, and filters; stream chronological activity entries. A separate live-update stream can emit new entries, but the reconnect contract must specify a resume token so no event is silently missed.

## Key Takeaways

- Streaming reduces memory pressure and time to first useful item.
- Backpressure does not eliminate the need for bounded queries.
- Cancellation is a normal lifecycle event.

## Interview Perspective

**Question:** How do you resume after a dropped stream? **Answer:** reconnect with a durable cursor/sequence and define duplicate handling. **Follow-up:** Why not return a list? A list delays all results and can exhaust memory for large datasets.

## Common Mistakes

- Ignoring `onCancel` and continuing expensive work.
- Streaming an infinite feed without a reconnect/resume design.
- Sending one huge protobuf message instead of useful chunks.

## What's Next

Continue with [Client Streaming](05-Client-Streaming.md).
