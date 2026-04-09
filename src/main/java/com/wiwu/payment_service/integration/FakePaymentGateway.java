package com.wiwu.payment_service.integration;

import com.wiwu.payment_service.integration.dto.PaymentRequest;
import com.wiwu.payment_service.integration.dto.PaymentResult;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Profile("teste")
public class FakePaymentGateway implements  PaymentGateway{

    @Override
    public PaymentResult processPayment(PaymentRequest request){

        String simulatedStatus = "approved";

        String externalReference = "FAKE-" + UUID.randomUUID();

        return new PaymentResult(simulatedStatus, externalReference);
    }

}
