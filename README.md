# Payment Service

## Idea

This project is a **backend payment service** created to study and demonstrate how **real payment systems work**, especially when payments are **not confirmed immediately**, such as with **PIX**.

The main goal is **not** to simulate a perfect payment, but to model the **correct backend behavior** for payments that depend on **external confirmation**.

---

## How It Works

### 1. Create a payment

A payment is created in the system and stored in the database.

At this moment, the payment **is not paid** — it only exists internally.

**Initial state:**
- `CREATED`

---

### 2. Start the payment

When the client initiates the payment, the backend sends the request to an external provider (PSP).

Because PIX is **asynchronous**, the system does **not assume approval**.

**State change:**
- `CREATED → PENDING`

---

### 3. Await confirmation (asynchronous)

The payment provider processes the payment independently.

When something changes, the provider **notifies the backend via webhook**.

Important:
- The webhook **does not decide anything**
- It only signals that **something changed**

---

### 4. Webhook processing

When the webhook is received:

1. The backend accepts the event
2. The backend queries the provider API
3. The provider response is treated as the **source of truth**
4. The domain applies the correct state transition

**Possible final states:**
- `PAID`
- `FAILED`

---

## State Model

The payment lifecycle is explicit and controlled only by the domain:

- `CREATED`
- `PENDING`
- `PAID`
- `FAILED`
- `CANCELLED`

This avoids implicit or unsafe transitions and mirrors real financial systems.

---

## Frontend Simulator

A **simple static frontend** is included only to:

- Call backend endpoints
- Visualize payment states
- Demonstrate the flow

The frontend **never makes payment decisions**.

All rules and transitions live in the backend.

---

## Why This Project

This project focuses on:

- Correct modeling of **asynchronous workflows**
- Understanding **webhook-based systems**
- Separation between **business logic** and **external providers**
- Realistic backend behavior instead of happy-path simulations

---

## Notes

- Sandbox environments have limitations and may not fully replicate real payment approvals
- The project is designed to be **honest and realistic**, not visually misleading

---

## Purpose

Educational project focused on backend architecture, payments, and event-driven design.
