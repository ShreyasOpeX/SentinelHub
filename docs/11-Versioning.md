# Versioning: Evolve Contracts Without Breaking Deployments

## Table of contents

- [Compatibility principle](#compatibility-principle)
- [Protobuf rules](#protobuf-rules)
- [API evolution strategy](#api-evolution-strategy)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## Compatibility principle

Services deploy independently, so old and new producers/consumers overlap. A change is safe only if a new client can talk to an old server and an old client can tolerate a new server for the required rollout window. Design additions as optional and keep existing meaning stable.

## Protobuf rules

Never change or reuse a released field number. Add a new numbered field instead; reserve removed field numbers/names. Keep zero enum as unspecified and write consumers that handle unknown enum values. Avoid changing a field’s wire type. Generated code should be regenerated from the committed schema in CI.

## API evolution strategy

Prefer additive messages and methods such as `GetCaseV2` only when a semantic break cannot be represented compatibly. Deprecate with a documented migration period, publish usage metrics, migrate consumers, then remove only after verification. For Kafka, version event schemas with the same discipline and make consumers tolerant of older/newer fields.

## Key Takeaways

- Field numbers are permanent public API.
- Additive change is cheaper than synchronized deployment.
- Version events as carefully as request APIs.

## Interview Perspective

**Question:** Can you rename a protobuf field? **Answer:** wire compatibility survives but source-level generated API changes; migrate consumers deliberately. **Follow-up:** What is the safest removal? Stop writing it, wait for readers, reserve it.

## Common Mistakes

- Reusing tags after deletion.
- Adding required business behavior without a fallback for old clients.
- Assuming a topic name alone versions an event contract.

## What's Next

Continue with [Kafka vs gRPC vs REST](12-Kafka-vs-gRPC-vs-REST.md).
