package com.Retail.Payment_service.ServiceImpl;


import com.Retail.Payment_service.Entity.Payment;
import com.Retail.Payment_service.Entity.PaymentStatus;
import com.Retail.Payment_service.Repository.PaymentRepository;
import com.Retail.Payment_service.Service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    @Override
    public Payment createPayment(Payment payment) {
        return repository.save(payment);
    }

    @Override
    public Payment getPayment(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
    }

    @Override
    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    @Override
    public Payment updatePayment(Long id, Payment payment) {

        Payment existing = getPayment(id);

        existing.setAmount(payment.getAmount());
        existing.setPaymentMethod(payment.getPaymentMethod());
        existing.setPaymentStatus(payment.getPaymentStatus());

        return repository.save(existing);
    }

    @Override
    public void deletePayment(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Payment> getPaymentsByCustomer(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    @Override
    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        return repository.findByPaymentStatus(status);
    }

    @Override
    public Payment refundPayment(Long id) {

        Payment payment = getPayment(id);

        payment.setPaymentStatus(PaymentStatus.REFUNDED);

        return repository.save(payment);
    }
}
