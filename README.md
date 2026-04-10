# Payment Service

## Idea

This project is a **backend payment service** created to study and demonstrate how **real payment systems work**, especially when payments are **not confirmed immediately**, such as with **PIX**.

The focus is not on simulating "instant payments", but on modeling the **correct backend behavior** for systems that depend on **external, asynchronous confirmation**.

---

## How It Works

### 1. Create a payment

A payment is created and persisted in the system. At this point, the payment is **not paid** — it only exists internally.

**Initial state:**
- `CREATED`

---

### 2. Start the payment

When the payment is initiated, the backend sends the request to an external provider (PSP).

Because PIX is **asynchronous**, the system does **not assume approval**.

**State change:**
- `CREATED → PENDING`

---

### 3. Await confirmation (asynchronous)

The payment provider processes the payment independently.

When a change occurs, the provider **notifies the backend via webhook**.

Important details:
- The webhook **does not decide the result**
- It only signals that **something changed**

---

### 4. Webhook processing

When the webhook is received:

1. The backend receives the event
2. The backend queries the provider API
3. The provider response is treated as the **source of truth**
4. The domain applies the correct state transition

**Possible final states:**
- `PAID`
- `FAILED`

---

## State Model

The payment lifecycle is explicit and controlled entirely by the domain:

- `CREATED`
- `PENDING`
- `PAID`
- `FAILED`
- `CANCELLED`

This avoids implicit transitions and mirrors real financial systems.

---

## Frontend Simulator

A **simple static frontend** is included only to:

- Call backend endpoints
- Visualize payment states
- Demonstrate the flow

The frontend **never makes payment decisions**.
All rules and transitions remain in the backend.

---

## Technologies Used

- Java
- Spring Boot
- PostgreSQL
- JPA / Hibernate
- Mercado Pago API (Sandbox)
- Webhooks
- Static HTML / JavaScript (demo frontend)

---

## Purpose

Educational project focused on backend architecture, payments, and event-driven design.

---

## Author

Developed by **Willian Wu**
