package com.wiwu.payment_service.integration.dto;

public class PaymentResult {

    private final String providerStatus;
    private final String externalReference;


    public PaymentResult(String providerStatus, String externalReference) {
        this.providerStatus = providerStatus;
        this.externalReference = externalReference;
    }

    public String getProviderStatus() {
        return providerStatus;
    }

    public String getExternalReference() {
        return externalReference;
    }
}
