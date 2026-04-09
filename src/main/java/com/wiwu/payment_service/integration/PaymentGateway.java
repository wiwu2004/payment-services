package com.wiwu.payment_service.integration;

import com.wiwu.payment_service.integration.dto.PaymentRequest;
import com.wiwu.payment_service.integration.dto.PaymentResult;

public interface PaymentGateway {

    PaymentResult processPayment(PaymentRequest request);


}
