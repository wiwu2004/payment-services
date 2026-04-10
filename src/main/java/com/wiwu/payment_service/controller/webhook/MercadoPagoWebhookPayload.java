package com.wiwu.payment_service.controller.webhook;

public class MercadoPagoWebhookPayload {

    private String type;
    private String action;
    private WebhookData data;

    public String getType() {
        return type;
    }

    public String getAction() {
        return action;
    }

    public WebhookData getData() {
        return data;
    }

    public static class WebhookData {
        private String id;

        public String getId() {
            return id;
        }
    }
}