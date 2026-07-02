package com.Retail.Order_service.ExternalUrl;


import com.Retail.Order_service.Entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "payment-service",
        url = "http://localhost:8082"
)
public interface PaymentClient {

    @PostMapping("/api/payments")
    Payment createPayment(@RequestBody Payment payment);
}
