package com.Retail.Order_service.ExternalUrl;

import com.Retail.Order_service.Entity.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "inventory-service",
        url = "http://localhost:8081"
)
public interface InventoryClient {

    @GetMapping("/api/inventory/{productId}")
    Inventory getInventory(@PathVariable("productId") Long productId);

    @PutMapping("/api/inventory/{productId}/{quantity}")
    Inventory updateInventory(
            @PathVariable Long productId,
            @PathVariable Integer quantity);

    @PutMapping("/api/inventory/add/{productId}/{quantity}")
    Inventory addToInventory(
            @PathVariable Long productId,
            @RequestBody Inventory inventory,
            @PathVariable Integer quantity);

}
