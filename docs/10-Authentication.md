# Authentication and Authorization in Service Calls

## Table of contents

- [Identity model](#identity-model)
- [Gateway and service responsibilities](#gateway-and-service-responsibilities)
- [Token handling](#token-handling)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## Identity model

Authentication answers “who is calling?”; authorization answers “may that identity perform this operation on this resource?” SentinelHub has two identities to consider: the end-user analyst and the workload/service. A secure design can propagate user claims for audit and fine-grained authorization while authenticating the service hop with mTLS or a workload token.

## Gateway and service responsibilities

The gateway authenticates external browser credentials, validates issuer/audience/signature/expiry, then forwards only trusted claims and trace context. Each internal service verifies its caller independently; it must not trust a plain `user-id` header because a compromised/internal caller could forge it. Case Service then evaluates rules such as whether the analyst may assign the requested case.

## Token handling

Send credentials in gRPC metadata over TLS. Cache signing keys safely, account for clock skew, rotate credentials, and redact values from logs. For long-lived streams, define expiry behavior: disconnect/re-authenticate or periodically refresh; never silently continue under an expired privilege.

## Key Takeaways

- TLS protects transport; it does not by itself establish user authorization.
- Authenticate each trust boundary and authorize at the resource owner.
- Identity propagation must be validated, minimal, and observable.

## Interview Perspective

**Question:** Why authenticate again behind the gateway? **Answer:** defense in depth and zero-trust boundaries; internal networks are not proof of identity. **Follow-up:** Where is case permission checked? In Case Service, which owns the case rule.

## Common Mistakes

- Treating a decoded JWT as validated without checking signature and claims.
- Logging tokens or putting them in protobuf business messages.
- Relying only on gateway authorization for every downstream action.

## What's Next

Continue with [Versioning](11-Versioning.md).
