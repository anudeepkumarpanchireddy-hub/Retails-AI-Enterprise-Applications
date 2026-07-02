package com.Retail.Payment_service.Repository;

import com.Retail.Payment_service.Entity.Payment;
import com.Retail.Payment_service.Entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByCustomerId(Long customerId);

    List<Payment> findByPaymentStatus(PaymentStatus status);

    List<Payment> findByOrderId(Long orderId);
}
