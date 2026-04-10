package com.wiwu.payment_service.controller.webhook;

import com.wiwu.payment_service.controller.webhook.MercadoPagoWebhookPayload;
import com.wiwu.payment_service.integration.mercadopago.MercadoPagoClient;
import com.wiwu.payment_service.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/webhooks/mercadopago")
public class MercadoPagoWebhookController {

    private final MercadoPagoClient mercadoPagoClient;
    private final PaymentService paymentService;

    public MercadoPagoWebhookController(
            MercadoPagoClient mercadoPagoClient,
            PaymentService paymentService
    ) {
        this.mercadoPagoClient = mercadoPagoClient;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Void> handleWebhook(
            @RequestBody MercadoPagoWebhookPayload payload
    ) {

        String mercadoPagoPaymentId = payload.getData().getId();

        Map<String, Object> payment =
                mercadoPagoClient.getPaymentById(mercadoPagoPaymentId);

        String status = (String) payment.get("status");
        String externalReference =
                (String) payment.get("external_reference");

        System.out.println("Status final MP: " + status);
        System.out.println("External reference: " + externalReference);

        paymentService.updateStatusFromProvider(
                externalReference,
                status
        );

        return ResponseEntity.ok().build();
    }
}