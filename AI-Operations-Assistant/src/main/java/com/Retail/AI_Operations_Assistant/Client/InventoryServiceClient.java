package com.Retail.AI_Operations_Assistant.Client;

import com.Retail.AI_Operations_Assistant.Entity.Inventory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import java.util.List;

@Service
public class InventoryServiceClient {

    private final RestClient restClient;

    @Value("${services.inventory.url}")
    private String inventoryServiceUrl;

    public InventoryServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }



    // Get Inventory
    public Inventory getInventory(Long productId) {
        return restClient.get()
                .uri(inventoryServiceUrl + "/api/inventory/{productId}", productId)
                .retrieve()
                .body(Inventory.class);
    }

    // Get All Inventory
    public List<Inventory> getAllInventory() {
        return restClient.get()
                .uri(inventoryServiceUrl + "/api/inventory")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Inventory>>() {});
    }


    // Add Inventory
    public Inventory addInventory(Long productId, Integer quantity) {
        return restClient.put()
                .uri(inventoryServiceUrl + "/api/inventory/add/{productId}/{quantity}",
                        productId, quantity)
                .retrieve()
                .body(Inventory.class);
    }

    // Delete Inventory
    public void deleteInventory(Long productId) {
        restClient.delete()
                .uri(inventoryServiceUrl + "/api/inventory/{productId}", productId)
                .retrieve()
                .toBodilessEntity();
    }

    // Inventory By Status
    public List<Inventory> getInventoryByStatus(String status) {
        return restClient.get()
                .uri(inventoryServiceUrl + "/api/inventory/status/{status}", status)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Inventory>>() {});
    }

    // Search Inventory
    public List<Inventory> searchInventory(String productName) {
        return restClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path(inventoryServiceUrl + "/api/inventory/search")
                                .queryParam("productName", productName)
                                .build())
                .retrieve()
                .body(new ParameterizedTypeReference<List<Inventory>>() {});
    }

    // Reserve Inventory
    public Inventory reserveInventory(Long productId, Integer quantity) {
        return restClient.put()
                .uri(uriBuilder ->
                        uriBuilder.path(inventoryServiceUrl +
                                        "/api/inventory/{productId}/reserve")
                                .queryParam("quantity", quantity)
                                .build(productId))
                .retrieve()
                .body(Inventory.class);
    }

    // Release Inventory
    public Inventory releaseInventory(Long productId, Integer quantity) {
        return restClient.put()
                .uri(uriBuilder ->
                        uriBuilder.path(inventoryServiceUrl +
                                        "/api/inventory/{productId}/release")
                                .queryParam("quantity", quantity)
                                .build(productId))
                .retrieve()
                .body(Inventory.class);
    }

    public List<Inventory> getLowStockProducts(Integer threshold) {

        return restClient.get()
                .uri(inventoryServiceUrl + "/inventory/low-stock?threshold={threshold}", threshold)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Inventory>>() {});
    }

}
