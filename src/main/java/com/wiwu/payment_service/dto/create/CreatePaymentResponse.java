package com.wiwu.payment_service.dto.create;

public class CreatePaymentResponse {
    private String paymentId;
    private String status;

    public CreatePaymentResponse(String paymentId, String status) {
        this.paymentId = paymentId;
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getStatus() {
        return status;
    }
}
