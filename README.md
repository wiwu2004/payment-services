# Payment Service

## 📌 Overview

This project is a **Payment Orchestration Microservice** built with **Java and Spring Boot**.
The service manages the **lifecycle of payments** in a clean, scalable and professional way, acting as a core backend service that can later integrate with external **Payment Service Providers (PSPs)**.

The main focus of this project is **architecture, domain modeling, and clean REST API design**, not just calling a payment SDK.

---

## 🎯 Project Goals

- Model real-world payment flows used in production systems
- Build a clean and maintainable Spring Boot REST API
- Apply best practices for:
  - Domain modeling
  - Persistence with JPA
  - Error handling
  - API design
- Prepare the foundation for future integration with external payment APIs (e.g. Mercado Pago, Stripe)

This project is intended to be **portfolio-quality**, following market standards.

---

## 🧱 Architecture Overview

The service follows a layered architecture:

- **Controller Layer**  
  Handles HTTP requests and responses.

- **Service Layer**  
  Contains business logic and orchestrates payment operations.

- **Domain Layer**  
  Represents core concepts such as `Payment` and `PaymentStatus`.

- **Persistence Layer**  
  Uses Spring Data JPA and Hibernate.

External payment providers are **not coupled** to the domain and will be integrated later through a dedicated integration layer.

---

## 💳 Payment Domain Model

### Payment

Represents a **payment intent**, not just a transaction.

Attributes:
- `id` (UUID)
- `amount`
- `method`
- `status`
- `createdAt`

### Payment Status

The payment lifecycle is represented by an enum:

- `CREATED`
- `PAID`
- `FAILED`
- `CANCELLED`

This ensures strong typing and valid state transitions.

---

## 🔗 API Endpoints

### Create Payment

```
POST /payments
```

**Request**
```json
{
  "amount": 100.00,
  "method": "PIX"
}
```

**Response**
```json
{
  "paymentId": "uuid",
  "status": "CREATED"
}
```

---

### Get Payment by ID

```
GET /payments/{paymentId}
```

**Response**
```json
{
  "id": "uuid",
  "amount": 100.00,
  "method": "PIX",
  "status": "CREATED",
  "createdAt": "2026-04-08T15:10:37"
}
```

---

## 🚨 Error Handling

The API uses **custom domain exceptions** and centralized error handling via `@RestControllerAdvice`.

Example:
- If a payment is not found, the API returns:

```
HTTP 404 Not Found
```

This keeps controllers clean and ensures correct REST semantics.

---

## 🗄️ Persistence

- Spring Data JPA with Hibernate
- UUIDs are used as identifiers
- Designed for easy migration to production databases (PostgreSQL / MySQL)
- H2 is used **only** for local development scaffolding

---

## 🛠️ Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven

---

## ✅ Current Status

- Core REST API implemented
- Payment domain modeled
- Persistence working
- Error handling implemented

The project is actively evolving.

---

## 🔮 Next Steps

- Payment state transitions (pay / cancel)
- Async payment flow
- Integration with a real Payment Service Provider (PSP)
- Webhook handling
- Production database setup

**Developed by : Willian Wu**
