package com.Retail.Payment_service.Controller;

import com.Retail.Payment_service.Entity.Payment;
import com.Retail.Payment_service.Entity.PaymentStatus;
import com.Retail.Payment_service.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    @GetMapping("/{id}")
    public Payment get(@PathVariable Long id) {
        return paymentService.getPayment(id);
    }

    @GetMapping
    public List<Payment> getAll() {
        return paymentService.getAllPayments();
    }

    @PutMapping("/{id}")
    public Payment update(
            @PathVariable Long id,
            @RequestBody Payment payment) {

        return paymentService.updatePayment(id, payment);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        paymentService.deletePayment(id);

        return "Payment deleted successfully.";
    }

    @GetMapping("/customer/{customerId}")
    public List<Payment> byCustomer(@PathVariable Long customerId) {

        return paymentService.getPaymentsByCustomer(customerId);
    }

    @GetMapping("/status/{status}")
    public List<Payment> byStatus(@PathVariable PaymentStatus status) {

        return paymentService.getPaymentsByStatus(status);
    }

    @PutMapping("/{id}/refund")
    public Payment refund(@PathVariable Long id) {

        return paymentService.refundPayment(id);
    }
}
