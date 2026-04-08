package com.wiwu.payment_service.service;

import com.wiwu.payment_service.dto.consult.PaymentResponse;
import com.wiwu.payment_service.dto.create.CreatePaymentRequest;
import com.wiwu.payment_service.dto.create.CreatePaymentResponse;
import com.wiwu.payment_service.entity.Payment;
import com.wiwu.payment_service.exception.PaymentNotFoundException;
import com.wiwu.payment_service.repository.PaymentsRepository;
import org.springframework.stereotype.Service;


@Service
public class PaymentService{
    private final PaymentsRepository paymentsRepository;

    public PaymentService(PaymentsRepository paymentsRepository){
        this.paymentsRepository = paymentsRepository;
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

}