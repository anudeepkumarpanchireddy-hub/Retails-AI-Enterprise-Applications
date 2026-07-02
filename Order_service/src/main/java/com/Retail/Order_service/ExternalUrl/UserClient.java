package com.Retail.Order_service.ExternalUrl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        url = "http://localhost:8083"
)
public interface UserClient {

    @GetMapping("/api/users/{id}/authenticated")
    Boolean isAuthenticated(@PathVariable("id") Long userId);
}
