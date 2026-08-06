# Why gRPC Exists

## Table of contents

- [The problem Google was solving](#the-problem-google-was-solving)
- [Why REST becomes costly internally](#why-rest-becomes-costly-internally)
- [The gRPC building blocks](#the-grpc-building-blocks)
- [REST, gRPC, and Kafka from first principles](#rest-grpc-and-kafka-from-first-principles)
- [Choosing a communication shape](#choosing-a-communication-shape)
- [SentinelHub communication architecture](#sentinelhub-communication-architecture)
- [Interview-ready definitions](#interview-ready-definitions)
- [Key Takeaways](#key-takeaways)
- [Interview Perspective](#interview-perspective)
- [Common Mistakes](#common-mistakes)
- [What's Next](#whats-next)

## The problem Google was solving

Google created gRPC to make service-to-service communication inside large distributed systems efficient, strongly typed, and capable of streaming. At that scale, a service is both a client and a server many times over. The cost of repeatedly parsing text, opening connections, manually maintaining API documentation, and waiting for sequential calls accumulates into latency, CPU usage, and operational ambiguity.

The essential model is a remote function call with an explicit contract. A developer defines a method and messages once; generated code gives callers a typed client and implementers a typed server surface. The network is still fallible, so gRPC does not make a remote call equivalent to a local function call—it makes the contract and transport more disciplined.

```text
Application code
  client.method(request)
       | generated stub serializes protobuf
       v
HTTP/2 connection -> remote generated server -> service implementation
```

### Evolution of service communication

```text
Function calls
  -> RPC: call a procedure across machines
  -> SOAP: standardized XML contracts, powerful but verbose
  -> REST: resource-oriented HTTP + usually JSON, web-friendly
  -> gRPC: typed protobuf RPC over HTTP/2 with native streams
  -> Event streaming: durable append-only facts consumed independently
```

This is not a replacement ladder. REST remains excellent for public web APIs; Kafka-style event streaming solves durable, asynchronous distribution rather than a synchronous query.

## Why REST becomes costly internally

REST is an architectural style that models resources exposed through HTTP methods: `GET /cases/42`, `POST /cases`. It usually uses JSON. This is approachable for humans and browsers, but its common HTTP/1.1 + JSON usage becomes expensive when high-volume internal services repeatedly exchange small typed messages.

### JSON and text-based serialization overhead

JSON includes field names and punctuation in every payload. A receiver allocates strings, tokenizes text, matches names, validates types, and builds objects. A binary protobuf encodes compact field identifiers and values, so it usually transmits fewer bytes and avoids text parsing. The benefit depends on payload shape and workload—compression, database latency, and business logic can dominate—but at high request rates the repeated work is material.

```text
JSON:     {"case_id":"C-42","priority":"HIGH"}
Protobuf: [field tag][C-42][field tag][enum value]

Text cost: field names + parsing + conversion on every request
Binary:    schema supplies names/types; wire sends compact values
```

REST also commonly creates a request/response pattern that works poorly for a large result: wait until a whole JSON array is built, serialize it, transmit it, then parse it. Pagination helps but requires repeated round trips. A stream can deliver records as they are available and hold only a bounded amount in memory.

### Typical REST architecture and use

```text
Browser/mobile -> HTTPS + JSON -> REST API -> services/databases
```

Use REST for browser-facing APIs, partner integrations, resource-oriented CRUD, simple webhooks, and cases where debuggability with ordinary HTTP tooling matters more than compactness. Advantages: ubiquitous browser support, caches/proxies/tooling, easy human inspection, and broad language adoption. Disadvantages: weaker contract enforcement unless OpenAPI/code generation is rigorously used, JSON overhead, no native bidirectional streaming model, and less efficient high-frequency internal fan-out.

Real-world examples include public payment, maps, Git hosting, and SaaS APIs; their external consumers value HTTP/JSON reachability. Many of the same organizations use RPC internally.

## The gRPC building blocks

gRPC combines Protocol Buffers, HTTP/2, generated stubs, and four call shapes.

### Protocol Buffers and binary serialization

Protocol Buffers define message fields by durable numeric tags and generate source code. The schema makes request/response types, enums, and method names explicit. This provides compile-time guidance and supports backward-compatible field addition when rules are followed. It is a contract system, not merely a faster JSON replacement.

### HTTP/2: one efficient connection, many streams

HTTP/2 permits multiplexing: several independent RPC streams share one persistent TCP connection. A slow history stream need not force a later case lookup to wait behind it at the application protocol layer. HTTP/2 header compression (HPACK) avoids retransmitting repeated headers such as authority and content type. Persistent connections avoid repeated TCP/TLS setup and permit efficient connection reuse.

```text
one HTTP/2 connection
  stream 1: GetCase -----------> response
  stream 3: StreamHistory -----> item -> item -> item
  stream 5: GetCustomer -------> response
```

Multiplexing does not defeat TCP-level packet loss effects, nor does it mean unlimited work is safe. Connection pools, deadlines, flow control, and load balancing still matter.

### Native streaming

gRPC supports unary, server-streaming, client-streaming, and bidirectional-streaming methods in its interface definition. Streaming solves large dataset problems by sending increments instead of buffering a complete collection; consumers receive early results and backpressure controls in-flight data. Bidirectional streaming exists when both sides must independently send messages over a long-lived session: live collaboration, interactive control channels, or continuous telemetry acknowledgements. It is not Kafka: a gRPC stream is a live connection, not a durable replayable log.

## REST, gRPC, and Kafka from first principles

### REST

**Definition:** a resource-oriented HTTP API style, typically JSON. **Why it exists:** the web needed a uniform, widely interoperable model. **Internal working:** clients issue HTTP method/path requests; servers return HTTP status, headers, and representations. **Use it when:** clients are browsers, partners, or diverse external developers. **Do not use it when:** a latency-sensitive internal typed call or full-duplex live channel is the primary need.

**Production example:** a browser calls `POST /cases` at the gateway. The browser receives familiar JSON and HTTP errors; the gateway maps the request to a typed internal call.

### gRPC

**Definition:** a contract-first RPC framework using protobuf and usually HTTP/2. **Why it exists:** internal systems need compact typed calls, multiplexing, and streams. **Internal working:** generated clients serialize protobuf messages onto HTTP/2 streams; generated server adapters deserialize, invoke handlers, and send messages/status. **Use it when:** a trusted service needs a quick typed answer or a managed live stream. **Do not use it when:** browser/partner accessibility, durable event retention, or offline consumers are the central requirement.

**Call shapes:** unary is one request/one response; server streaming is one request/many results; client streaming is many uploads/one summary; bidirectional streaming lets both sides send independently. **Production example:** gateway-to-CaseService `CreateCase`, or history results streamed to an internal export worker.

### Kafka

**Definition:** a distributed, durable event log and streaming platform. **Why it exists:** producers should publish facts without synchronously knowing every downstream consumer, and consumers should replay history. **Producer:** appends records. **Consumer:** reads records and commits progress. **Topic:** named event category. **Partition:** ordered, scalable shard; order is guaranteed only within a partition. 

```text
Case Service -- CaseCreated --> topic: case-events
                                      | partition by caseId
                      +---------------+----------------+
                      v                                v
              History consumer                  Notification consumer
```

Use Kafka for event-driven architecture, audit trails, integration, retries, and work that need not complete before the original caller receives a response. Do not use it for a synchronous read where the user needs an immediate answer, or for request/reply without careful correlation/timeouts. Advantages: decoupling, retention/replay, consumer scaling, and durable fan-out. Disadvantages: eventual consistency, operational complexity, duplicate/out-of-order handling across partitions, and harder tracing. Real-world examples include order events feeding inventory, analytics, fraud, email, and audit systems.

### Comparison

| Dimension | REST | gRPC | Kafka |
|---|---|---|---|
| Communication style | Request/response resources | Typed RPC and live streams | Asynchronous append/consume events |
| Payload format | Usually JSON/text | Protobuf binary by default | Any bytes; commonly Avro/Protobuf/JSON |
| Performance | Good, more text overhead | High for internal calls | High throughput, not immediate response |
| Streaming support | Limited/SSE/WebSocket adjuncts | Native four RPC shapes | Continuous durable consumption |
| Browser support | Native | Needs gRPC-Web/proxy | No direct browser use |
| External APIs | Excellent | Selective | Poor fit |
| Internal APIs | Good | Excellent for synchronous calls | Excellent for async integration |
| Type safety | Convention/OpenAPI tooling | Strong schema + generated stubs | Depends on event schema discipline |
| Event-driven support | Webhooks/polling | Not durable events | Core purpose |
| Best use cases | Public CRUD and web APIs | Service calls, low latency, streams | Durable fan-out and replay |

## Choosing a communication shape

```text
Need a response before proceeding?
  no -> Must events be retained/replayed or fan out independently?
          yes -> Kafka
          no  -> async job/queue may fit
  yes -> One request and bounded answer? -> Unary RPC
         One request, progressive many answers? -> Server streaming
         Many client chunks, one final summary? -> Client streaming
         Both sides speak independently while connected? -> Bidirectional streaming
```

- **Unary:** create a case or fetch a customer now.
- **Server streaming:** progressively return a filtered case history or export.
- **Client streaming:** upload many investigation evidence chunks, then return validation/import summary.
- **Bidirectional:** live analyst presence/cursors where client and server both push changes.
- **Kafka:** after `CaseCreated`, notify, index, audit, and analyze without delaying case creation.

## SentinelHub communication architecture

```text
Browser
  | REST / JSON: universal browser contract, auth/cookies, HTTP semantics
  v
Gateway
  | gRPC / protobuf / HTTP/2: typed internal commands and low-latency queries
  +--> Case Service
  +--> Customer Service
  +--> History Service
  +--> Collaboration Service
          |
          | Kafka domain events: durable decoupled fan-out
          v
     Notification Service
```

The gateway shields browsers from internal topology and translates external DTOs/errors. gRPC lets the gateway and owning services share generated contracts, explicit deadlines, and identity metadata. Kafka sits after state-changing operations: an accepted case should not wait for email/SMS delivery, and a notification consumer must be able to retry or replay events. History may also consume events to create an immutable audit trail. Each service owns its data; Kafka distributes facts, not database tables.

## Interview-ready definitions

### RPC and unary RPC

**Definition:** RPC invokes a named remote operation; unary has one request and one response. **Why:** it models immediate command/query interactions. **Working:** a stub serializes, opens an HTTP/2 stream, then receives a response/status. **Use:** bounded queries and idempotent or idempotency-protected commands. **Avoid:** long-running/replayable jobs. **Question:** “What happens on timeout?” The outcome is unknown; retry only safely. **Mistake:** assuming local-call failure semantics. **Example:** `GetCase`.

### Streaming RPC

**Definition:** an RPC exchanging multiple messages on a live HTTP/2 stream. **Why:** progressive results and interactive sessions. **Working:** message framing and flow control regulate delivery until completion/cancellation. **Use:** histories, ingestion, collaboration. **Avoid:** durable notification workflows. **Question:** “How do clients recover?” Use a cursor/resume protocol. **Mistake:** no bounds/cancellation handling. **Example:** stream case history.

### REST

**Definition:** resource-oriented HTTP interface. **Why:** interoperable web access. **Working:** methods, URLs, status codes, representations. **Use:** browsers/partners. **Avoid:** chatty internal high-frequency calls. **Question:** “Why at gateway?” Browser compatibility. **Mistake:** exposing microservice internals. **Example:** browser `POST /cases`.

### Kafka event

**Definition:** immutable fact appended to a partitioned topic. **Why:** decouple and retain integration work. **Working:** producer appends; consumer reads and tracks offset. **Use:** notifications/audit/analytics. **Avoid:** synchronous authoritative reads. **Question:** “Is order global?” No, only per partition. **Mistake:** assuming exactly-once business effect. **Example:** `CaseCreated` consumed by notifications.

## Key Takeaways

- gRPC exists to make internal distributed calls typed, compact, multiplexed, and stream-capable.
- REST, gRPC, and Kafka are complementary boundaries, not competing defaults.
- A live gRPC stream is not a durable Kafka log; design reconnect and replay separately.

## Interview Perspective

**Question:** Why not use Kafka for every service interaction? **Expected answer:** callers needing an immediate answer would inherit eventual consistency, correlation, timeout, and operational complexity. Use gRPC for synchronous ownership queries/commands and Kafka for independent durable reactions. **Follow-up:** How do you guarantee notification after a DB change? Use an outbox or equivalent atomic publication pattern. **Misconception:** protobuf automatically makes an API fast; data stores and topology still dominate latency.

## Common Mistakes

- Exposing raw gRPC directly to browsers without a gRPC-Web/proxy strategy.
- Treating Kafka as a database query API or assuming global message order.
- Streaming unbounded data without cursors, cancellation, and limits.
- Retrying non-idempotent RPCs after a deadline.

## What's Next

Continue with [Protocol Buffers](02-Protocol-Buffers.md).
