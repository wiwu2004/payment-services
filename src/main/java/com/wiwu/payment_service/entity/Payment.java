package com.wiwu.payment_service.entity;
import com.wiwu.payment_service.enums.PaymentStatus;
import com.wiwu.payment_service.exception.InvalidPaymentStateException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String method;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Payment(){
    }

    public Payment(BigDecimal amount, String method) {
        this.amount = amount;
        this.method = method;
        this.status = PaymentStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getMethod() {
        return method;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public void markAsPaid(){
        this.status = PaymentStatus.PAID;
    }

    public void cancel(){
        if (this.status != PaymentStatus.CREATED){
            throw new InvalidPaymentStateException("Payment cannot be cancelled in Status: " + this.status);
        }
        this.status = PaymentStatus.CANCELLED;
    }

    public void markAsFailed() {
        this.status = PaymentStatus.FAILED;
    }

    public void markAsPending() {
        this.status = PaymentStatus.PENDING;
    }

}
