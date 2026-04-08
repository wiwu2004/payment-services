package com.wiwu.payment_service.dto.consult;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {
    private String id;
    private BigDecimal amount;
    private String method;
    private String status;
    private LocalDateTime createdAt;

    public PaymentResponse(String id,BigDecimal amount, String method, String status, LocalDateTime createdAt) {
        this.amount = amount;
        this.id = id;
        this.method = method;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }
}
