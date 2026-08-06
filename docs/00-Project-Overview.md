# SentinelHub Project Overview

## Table of contents

- [Purpose](#purpose)
- [Domain and boundaries](#domain-and-boundaries)
- [Current repository state](#current-repository-state)
- [How to learn with this repository](#how-to-learn-with-this-repository)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## Purpose

SentinelHub models an incident-investigation platform. Analysts create and assign cases, look up customers, inspect history, collaborate in real time, and trigger notifications. It is designed to teach a practical rule: choose communication based on the interaction's ownership, latency, audience, and delivery guarantee—not because a technology is fashionable.

## Domain and boundaries

`Case Service` owns case lifecycle. `Customer Service` owns customer facts. `History Service` records immutable activity. `Collaboration Service` owns live analyst sessions. `Notification Service` converts durable events into delivery attempts. The gateway is an adapter, not a business-data owner.

```text
HTTP client -> Gateway -> synchronous gRPC query/command -> owning service
                                                    |
                                                    +-> durable domain event -> Kafka -> interested services
```

## Current repository state

The Maven parent declares Java 21, Spring Boot, gRPC, and protobuf dependencies. The `proto` module contains the executable `CaseService` schema with unary Create, Get, UpdateStatus, and Assign methods. The named service modules are scaffolded; their runtime implementations and Kafka wiring are a development roadmap, not a claim about code already present.

## How to learn with this repository

Read the guides in numeric order. Start with why an internal RPC boundary helps, then read the `.proto` file beside the Protocol Buffers and Unary RPC chapters. Treat every proposed interaction as a design exercise: who owns the answer, must the caller wait, can the work be replayed, and does a browser need to call it?

## Key Takeaways

- A service boundary is an ownership boundary, not merely a network hop.
- The existing protobuf module is the shared contract source of truth.
- Synchronous gRPC and asynchronous Kafka serve different failure and timing models.

## Interview Perspective

**Question:** Why not let all services read one database? **Answer:** it couples schema changes, availability, and deployments; ownership becomes unclear. A follow-up is how to share data: publish facts/events and create local read models where justified.

## Common Mistakes

- Treating the gateway as a place to duplicate domain rules.
- Assuming service names mean implementations already exist.
- Using events for a request that needs an immediate, authoritative answer.

## What's Next

Continue with [Why gRPC](01-Why-gRPC.md).
