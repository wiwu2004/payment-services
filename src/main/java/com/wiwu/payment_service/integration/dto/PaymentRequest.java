package com.wiwu.payment_service.integration.dto;

import java.math.BigDecimal;

public class PaymentRequest {

    private final String paymentId;
    private final BigDecimal amount;
    private  final String method;

    public PaymentRequest(String paymentId, BigDecimal amount, String method) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.method = method;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }
}
