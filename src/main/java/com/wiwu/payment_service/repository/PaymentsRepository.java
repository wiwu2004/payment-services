package com.wiwu.payment_service.repository;

import com.wiwu.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payment,String> {
}
