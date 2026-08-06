# Deadlines: A Latency Budget, Not a Timeout Afterthought

## Table of contents

- [Definition](#definition)
- [Deadline propagation](#deadline-propagation)
- [Retries and cancellation](#retries-and-cancellation)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## Definition

A gRPC deadline is the latest time at which a caller is willing to wait for an RPC. It travels as a remaining time budget, unlike an arbitrary server timeout. When it expires, the client receives `DEADLINE_EXCEEDED`; server work should observe cancellation and stop when safe.

## Deadline propagation

Start with the browser/API latency objective. The gateway assigns a finite budget, then each downstream call receives less remaining time after gateway work. A service must not create a fresh large timeout for every nested call, or a 500 ms user request can cause seconds of useless work after the user has left.

```text
800 ms inbound budget
  gateway validation: 80 ms
  CaseService budget: <=720 ms
    CustomerService budget: only remaining time, e.g. 450 ms
```

## Retries and cancellation

Retry only transient failures, only when there is time remaining, and only for idempotent operations or idempotency-key-protected commands. Use bounded attempts with jitter; retries consume the latency budget and can amplify an outage. `DEADLINE_EXCEEDED` is ambiguous: the server might have committed the command, so query by idempotency key rather than blindly create again.

## Key Takeaways

- Every network call needs a deliberate finite deadline.
- Propagate remaining budget, not independent long timeouts.
- Cancellation handling protects capacity under load.

## Interview Perspective

**Question:** Deadline versus timeout? **Answer:** a deadline is an absolute/latest completion target propagated as remaining budget; a timeout is often a local duration. **Follow-up:** Can a timed-out request succeed? Yes, remotely; treat outcome as unknown.

## Common Mistakes

- Using infinite deadlines.
- Retrying after the deadline has no useful time left.
- Swallowing cancellation and completing expensive downstream work.

## What's Next

Continue with [Authentication](10-Authentication.md).
