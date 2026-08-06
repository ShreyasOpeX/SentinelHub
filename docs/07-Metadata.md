# gRPC Metadata: Context That Is Not Business Data

## Table of contents

- [Definition](#definition)
- [Appropriate contents](#appropriate-contents)
- [Propagation](#propagation)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## Definition

Metadata is key-value data sent with gRPC headers or trailers. It carries call context—authorization credentials, correlation IDs, locale, and tracing—not the business request itself. Headers arrive before messages; trailers accompany final status.

## Appropriate contents

Use lowercase ASCII keys, and suffix binary values with `-bin`. Put actor identity tokens, request/trace IDs, client version, and deadline-related observability context in metadata. Put case IDs, filters, and validation-relevant domain inputs in protobuf messages, where they are explicit and schema-versioned.

## Propagation

At SentinelHub’s edge, the gateway validates the browser credential then forwards a constrained service identity/trace context downstream. The receiving interceptor verifies and places trusted identity in request context. Do not blindly forward every incoming header: it can leak credentials, exceed header limits, or permit spoofing.

## Key Takeaways

- Metadata is cross-cutting call context, not an untyped escape hatch.
- Validate and allow-list propagated values at trust boundaries.
- Trailers are useful for terminal diagnostics, not normal business responses.

## Interview Perspective

**Question:** Why not put JWT in every protobuf request? **Answer:** authentication is cross-cutting transport context; putting it in every message duplicates it and muddles domain contracts. **Follow-up:** How is it secured? TLS plus server-side validation.

## Common Mistakes

- Passing sensitive headers to logs.
- Trusting caller-provided user IDs without token validation.
- Putting large payloads or mutable business flags in metadata.

## What's Next

Continue with [Interceptors](08-Interceptors.md).
