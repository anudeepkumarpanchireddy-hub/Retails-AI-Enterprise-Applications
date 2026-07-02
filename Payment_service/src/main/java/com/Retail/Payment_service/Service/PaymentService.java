package com.Retail.Payment_service.Service;


import com.Retail.Payment_service.Entity.Payment;
import com.Retail.Payment_service.Entity.PaymentStatus;

import java.util.List;

public interface PaymentService {

    Payment createPayment(Payment payment);

    Payment getPayment(Long id);

    List<Payment> getAllPayments();

    Payment updatePayment(Long id, Payment payment);

    void deletePayment(Long id);

    List<Payment> getPaymentsByCustomer(Long customerId);

    List<Payment> getPaymentsByStatus(PaymentStatus status);

    Payment refundPayment(Long id);
}
