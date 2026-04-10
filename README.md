# Payment Service

## Overview

This project is a **backend payment service** designed to model **real-world asynchronous payment flows**, with a special focus on **PIX** and **webhook-based confirmation**. It reflects how payments work in production systems, where approval is **not immediate** and must be confirmed through external events.

The project was built with an emphasis on **domain correctness**, **event-driven thinking**, and **clean separation between business rules and external providers**.

---

## Key Concepts

- Payments are **stateful and asynchronous**
- The backend must **not assume approval** at creation time
- External providers (PSPs) notify state changes via **webhooks**
- The system reacts to events and applies **idempotent state transitions**

---

## Domain Model

### Payment States

The `Payment` entity moves explicitly through the following states:

- `CREATED` – payment created internally
- `PENDING` – payment sent to provider, awaiting confirmation
- `PAID` – payment approved by provider
- `FAILED` – payment rejected or failed
- `CANCELLED` – payment cancelled by user or system

State transitions are handled **only by the domain**, never directly by controllers or external integrations.

---

## Architecture

The service follows a clean and decoupled architecture:

```
Controller
  ↓
Service (Domain Logic)
  ↓
PaymentGateway (Interface)
  ↓
External Provider (or Fake)
```

### Key Architectural Decisions

- **Gateway pattern** to isolate PSP integration
- **Webhook-driven confirmation**, treated as external events
- **PostgreSQL** as the primary persistent store
- **No in-memory assumptions** (no H2, no volatile state)

---

## Payment Flow

### 1. Create Payment

```
POST /payments
```

- Persists a new payment
- Initial state: `CREATED`

### 2. Initiate Payment

```
POST /payments/{paymentId}/pay
```

- Sends payment to provider
- State becomes `PENDING`

### 3. Await Confirmation (Asynchronous)

- Provider processes the payment
- Provider sends an event to the webhook endpoint

### 4. Webhook Handling

```
POST /webhooks/mercadopago
```

- Webhook receives an event
- Backend **queries the provider API** for the authoritative state
- Domain updates payment state accordingly

The webhook is treated as **a notification, not a source of truth**.

---

## Webhook Validation

Due to **sandbox limitations of PIX**, automatic approval events are not always emitted.

To address this during development:

- The webhook handler is fully implemented and validated structurally
- The backend safely receives events, processes payloads, and reacts correctly
- Errors from external providers never break the system

This mirrors **real backend practice**, where infrastructure and behavior are tested independently.

---

## Frontend Simulator

A **static frontend** is included solely for visualization and demonstration purposes.

The frontend:

- Calls backend endpoints
- Displays payment states
- Allows controlled webhook simulation

**The frontend does not make payment decisions.**
All state transitions remain in the backend domain.

---

## Persistence

- Database: **PostgreSQL**
- ORM: **JPA / Hibernate**
- Enums persisted as `VARCHAR`
- Schema managed explicitly (no auto-drop for production)

---

## Technology Stack

- Java
- Spring Boot
- PostgreSQL
- JPA / Hibernate
- Mercado Pago API (Sandbox)
- Webhooks
- Static HTML frontend for demo

---

## Why This Project Matters

This project focuses on **correct payment design**, not happy-path demos.

It demonstrates:

- Understanding of **eventual consistency**
- Proper **asynchronous workflow modeling**
- Realistic **PSP integration patterns**
- Defensive webhook handling

---

## Disclaimer

This project is intended for **learning and demonstration purposes**.

Actual payment processing in production environments requires:

- Production credentials
- Secure deployment
- HTTPS endpoints
- Provider validation and compliance

---

## Author

**Willian Wu**
