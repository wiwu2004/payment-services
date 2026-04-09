# Payment Service

## 📌 Overview

**Payment Service** is a backend **Payment Orchestration Microservice** built with **Java and Spring Boot**, designed to model real-world payment flows used in production systems.  
The service focuses on **clean architecture, proper domain modeling, and real PSP integration**, rather than simple SDK-based demos.

The project currently integrates with **Mercado Pago (Sandbox)** using **direct REST API calls**, following best practices used in fintech and large-scale systems.

---

## 🎯 Project Goals

- Model real-world payment lifecycles (synchronous and asynchronous)
- Build a production-ready REST API
- Apply best practices in:
  - Domain-driven design
  - Payment state management
  - Error handling
  - External API integration
- Avoid tight coupling with payment providers
- Prepare the system for real production environments

---

## 🧱 Architecture Overview

The service follows a **clean, layered architecture**:

- **Controller Layer**  
  Exposes REST endpoints and handles HTTP concerns.

- **Service Layer**  
  Orchestrates payment flows and enforces business rules.

- **Domain Layer**  
  Encapsulates payment rules and valid state transitions.

- **Integration Layer (Gateway Pattern)**  
  Handles communication with external Payment Service Providers (PSPs).

External providers are accessed exclusively through a **PaymentGateway interface**, ensuring complete decoupling from the domain.

---

## 💳 Payment Domain Model

### Payment

A `Payment` represents a **payment intent**, not just a transaction.

**Core fields:**
- `id` (UUID)
- `amount`
- `method` (PIX, etc.)
- `status`
- `createdAt`

### Payment Status Lifecycle

The domain models real payment behavior:

- `CREATED` – Payment intent created internally
- `PENDING` – Payment created in PSP, awaiting confirmation (PIX)
- `PAID` – Payment successfully completed
- `FAILED` – Payment failed or rejected
- `CANCELLED` – Payment cancelled before completion

This lifecycle accurately represents **asynchronous payment flows**.

---

## 🔌 Payment Gateway Abstraction

The project uses a **Gateway / Adapter pattern** for PSP integration.

### PaymentGateway (Port)

Defines the contract used by the domain:

- `processPayment(PaymentRequest request)`

### Implementations

- **FakePaymentGateway** (`dev` profile)  
  Simulates external providers for development and testing.

- **MercadoPagoPaymentGateway** (`sandbox` profile)  
  Real integration using Mercado Pago REST API.

Gateway selection is controlled via **Spring Profiles**, not code changes.

---

## 🌐 Mercado Pago Integration (Sandbox)

The system integrates with **Mercado Pago Checkout Transparente** using **pure REST calls**:

- No SDKs inside the domain
- Secure token handling via environment variables
- Required headers handled correctly:
  - `Authorization: Bearer <ACCESS_TOKEN>`
  - `X-Idempotency-Key`

### PIX Flow Behavior

- Payment creation returns `pending`
- Final confirmation occurs asynchronously (via webhook)
- Domain status correctly reflects real-world behavior

---

## 🔗 REST API Endpoints

### Create Payment

```
POST /payments
```

```json
{
  "amount": 10.00,
  "method": "PIX"
}
```

---

### Pay Payment

```
POST /payments/{paymentId}/pay
```

Initial response for PIX:

```json
{
  "status": "PENDING"
}
```

---

### Get Payment

```
GET /payments/{paymentId}
```

---

## ⚠️ Error Handling

The API follows HTTP semantics:

- `404 Not Found` – Payment does not exist
- `409 Conflict` – Invalid state transition
- Clear domain-level exceptions

All errors are handled centrally using `@RestControllerAdvice`.

---

## 🗄️ Persistence

- Spring Data JPA
- Hibernate ORM
- UUID-based identifiers
- Designed for easy migration to production databases (PostgreSQL/MySQL)
- H2 used only for local development

---

## ✅ Current Status

- ✅ Real Mercado Pago sandbox integration
- ✅ Asynchronous PIX flow modeled correctly
- ✅ Gateway abstraction in place
- ✅ Idempotent payment creation
- ✅ Production-grade error handling
- ✅ Domain-driven payment lifecycle

---

## 🔮 Next Steps

- Webhook handling for PIX confirmation
- Persist external PSP references
- Observability and metrics
- Production environment setup
- Security hardening

---

## 🛠️ Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven
- Mercado Pago REST API

---

**Developed by Willian Wu**
