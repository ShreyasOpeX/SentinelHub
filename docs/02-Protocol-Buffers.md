# Protocol Buffers: Contracts Before Code

## Table of contents

- [What a protobuf contract is](#what-a-protobuf-contract-is)
- [How serialization works](#how-serialization-works)
- [Schema evolution](#schema-evolution)
- [SentinelHub example](#sentinelhub-example)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## What a protobuf contract is

Protocol Buffers (protobuf) is an interface-definition language and binary serialization format. A `.proto` file declares messages, field numbers, enums, and services. Code generation turns that contract into Java message classes and gRPC client/server stubs. It makes the wire shape explicit and compile-time checked.

## How serialization works

Each encoded field carries its field number and wire type, followed by a compact value. Names are not sent. Unknown fields can be ignored by an older consumer, which is why a message may evolve safely when its numbers remain stable. Generated messages are immutable builders in Java, avoiding hand-written JSON mapping drift.

```proto
message GetCaseRequest { string case_id = 1; }
message CaseResponse { string case_id = 1; string title = 2; }
```

## Schema evolution

Add optional fields with new numbers; preserve old behavior when they are absent. Never renumber, reuse, or change the meaning of a released field number. Reserve removed names and numbers. Prefer a new field over changing `string` to an incompatible semantic type; enums must include an unspecified zero value so old clients handle new values defensively.

## SentinelHub example

`case_service.proto` uses `google.protobuf.Timestamp` rather than a formatted date string, and its enums begin with an unspecified value. These are production-oriented contract choices: timestamps have an unambiguous representation and zero values remain safe.

## Key Takeaways

- Field numbers, not field names, are the durable wire identity.
- Generate code from one shared schema; do not copy DTOs between services.
- Backward compatibility is an operational requirement during rolling deployment.

## Interview Perspective

**Question:** Can field 3 be renamed? **Answer:** yes, wire compatibility remains because its number and type remain stable, but generated API consumers may need migration. **Follow-up:** Can it be reused after deletion? No—reserve it forever.

## Common Mistakes

- Renumbering for visual order.
- Making a new enum value the default behavior without handling it.
- Treating a protobuf schema as an internal implementation detail.

## What's Next

Continue with [Unary RPC](03-Unary-RPC.md).
