# gRPC Performance: Measure the Whole Request Path

## Table of contents

- [Performance model](#performance-model)
- [Practical levers](#practical-levers)
- [Load-testing plan](#load-testing-plan)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## Performance model

End-to-end latency includes queueing, DNS/connection setup, TLS, serialization, network transfer, service scheduling, database/cache calls, and downstream fan-out. Protobuf and HTTP/2 improve wire and connection efficiency; they cannot compensate for an unindexed case query or an overloaded dependency.

## Practical levers

Reuse channels rather than create one per RPC. Set bounded deadlines and concurrency limits. Keep protobuf messages focused; stream large results; configure maximum message size defensively. Instrument p50/p95/p99 latency, status code, in-flight streams, retries, cancellation, connection health, and downstream timing. Compress only after measurement: compressing tiny payloads can cost more CPU than bytes saved.

## Load-testing plan

Test realistic mixes: case reads, writes, history streams, slow clients, deadline expiry, and downstream failure. Establish a baseline, vary concurrency gradually, locate queueing knees, and verify graceful degradation. Include correctness checks—no duplicate case creation, no leaked streams—not only requests per second.

## Key Takeaways

- Performance is a tail-latency and capacity problem, not a protocol slogan.
- Channel reuse, small contracts, deadlines, and observability are high-value defaults.
- Tune after measuring realistic workload and failure modes.

## Interview Perspective

**Question:** Why can HTTP/2 multiplexing still suffer? **Answer:** TCP packet loss, service queues, CPU, and downstream bottlenecks remain. **Follow-up:** First optimization? Measure traces and tail latency before changing compression or thread pools.

## Common Mistakes

- Creating a channel for every request.
- Reporting only average latency.
- Increasing timeouts to hide saturation.

## What's Next

Continue with [Interview Questions](14-Interview-Questions.md).
