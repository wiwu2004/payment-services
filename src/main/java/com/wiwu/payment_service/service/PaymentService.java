package com.wiwu.payment_service.service;

import com.wiwu.payment_service.dto.consult.PaymentResponse;
import com.wiwu.payment_service.dto.create.CreatePaymentRequest;
import com.wiwu.payment_service.dto.create.CreatePaymentResponse;
import com.wiwu.payment_service.entity.Payment;
import com.wiwu.payment_service.enums.PaymentStatus;
import com.wiwu.payment_service.exception.InvalidPaymentStateException;
import com.wiwu.payment_service.exception.PaymentNotFoundException;
import com.wiwu.payment_service.integration.PaymentGateway;
import com.wiwu.payment_service.integration.dto.PaymentRequest;
import com.wiwu.payment_service.integration.dto.PaymentResult;
import com.wiwu.payment_service.repository.PaymentsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PaymentService  {

    private final PaymentsRepository paymentsRepository;
    private final PaymentGateway paymentGateway;

    public PaymentService(PaymentsRepository paymentsRepository,PaymentGateway paymentGateway ){
        this.paymentsRepository = paymentsRepository;
        this.paymentGateway = paymentGateway;
    }


    public CreatePaymentResponse createPayment(CreatePaymentRequest request){

        Payment payment = new Payment(
                request.getAmount(),
                request.getMethod()
        );

        Payment savedPayment = paymentsRepository.save(payment);

        return  new CreatePaymentResponse(
                savedPayment.getId(),
                savedPayment.getStatus().name()
        );
    }

    public PaymentResponse getPaymentById(String paymentId){
        Payment payment = paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        return new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getStatus().name(),
                payment.getCreatedAt()
        );
    }

    @Transactional
    public PaymentResponse pay(String paymentId) {

        Payment payment = paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        if (payment.getStatus() != PaymentStatus.CREATED) {
            throw new InvalidPaymentStateException("Payment cannot be paid in current state");
        }


        PaymentRequest request = new PaymentRequest(
                payment.getId(),
                payment.getAmount(),
                payment.getMethod()
        );

        PaymentResult result = paymentGateway.processPayment(request);

       switch (result.getProviderStatus()){
           case "approved" -> payment.markAsPaid();
           case "pending", "in_process" -> payment.markAsPending();
           case "rejected" -> payment.markAsFailed();
           default -> throw  new IllegalStateException("Unknown provider status: " + result.getProviderStatus());
       }

        return new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getStatus().name(),
                payment.getCreatedAt()
        );}

    @Transactional
    public PaymentResponse cancel(String paymentId){
        Payment payment = paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        payment.cancel();

        return  new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getStatus().name(),
                payment.getCreatedAt()
        );
    }

    @Transactional
    public void updateStatusFromProvider(String paymentId, String providerStatus) {

        Payment payment = paymentsRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        switch (providerStatus) {
            case "approved" -> payment.markAsPaid();
            case "rejected" -> payment.markAsFailed();
            default -> {
            }
        }
    }
}

