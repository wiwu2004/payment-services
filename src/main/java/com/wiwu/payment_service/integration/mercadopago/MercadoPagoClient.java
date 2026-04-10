package com.wiwu.payment_service.integration.mercadopago;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class MercadoPagoClient {
    private final RestTemplate restTemplate;
    private final String acessToken;

    public MercadoPagoClient(RestTemplate restTemplate, @Value("${mercadopago.access-token}") String acessToken) {
        this.restTemplate = restTemplate;
        this.acessToken = acessToken;
    }


    public Map<String, Object> getPaymentById(String paymentId) {

        String url = "https://api.mercadopago.com/v1/payments/" + paymentId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(acessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return  response.getBody();

    }
}
