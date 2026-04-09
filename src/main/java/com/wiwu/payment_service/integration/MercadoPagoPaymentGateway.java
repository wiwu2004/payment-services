package com.wiwu.payment_service.integration;

import com.wiwu.payment_service.integration.dto.PaymentRequest;
import com.wiwu.payment_service.integration.dto.PaymentResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Profile("sandbox")
public class MercadoPagoPaymentGateway implements PaymentGateway{

    private  final RestTemplate restTemplate;
    private final String acessToken;

    public MercadoPagoPaymentGateway(
            RestTemplate restTemplate,
            @Value("${mercadopago.access-token}") String acessToken
    ) {
        this.restTemplate = restTemplate;
        this.acessToken = acessToken;
    }


    @Override
    public PaymentResult processPayment(PaymentRequest request){
        String url = "https://api.mercadopago.com/v1/payments";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(acessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Idempotency-Key", request.getPaymentId());

        Map<String,Object> body = new HashMap<>();

        body.put("transaction_amount", request.getAmount());
        body.put("payment_method_id", "pix");
        body.put("external_reference", request.getPaymentId());
        body.put("description", "Payment " + request.getPaymentId());

        Map<String, Object> identification = new HashMap<>();
        identification.put("type", "CPF");
        identification.put("number", "12345678909");

        Map<String,Object> payer = new HashMap<>();
        payer.put("email", "test@test.com");
        payer.put("identification", identification);

        body.put("payer", payer);

        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        String status = (String) response.getBody().get("status");
        System.out.println("Mercado Pago raw status: " + status);
        boolean sucess = "approved".equalsIgnoreCase(status);

        return new PaymentResult(status, response.getBody().get("id").toString());
    }

}
