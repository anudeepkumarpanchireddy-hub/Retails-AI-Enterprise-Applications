package com.Retail.Inventory_service.Controller;


import com.Retail.Inventory_service.Entity.Inventory;
import com.Retail.Inventory_service.Entity.InventoryStatus;
import com.Retail.Inventory_service.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }

    @GetMapping("/{productId}")
    public Inventory getInventory(@PathVariable Long productId) {
        return inventoryService.getInventoryByProductId(productId);
    }

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/low-stock")
    public List<Inventory> getLowStockProducts(
            @RequestParam(defaultValue = "10") Integer threshold) {

        return inventoryService.getLowStockProducts(threshold);
    }

    @PutMapping("/{productId}/{quantity}")
    public Inventory updateInventory(
            @PathVariable Long productId,
            @PathVariable Integer quantity) {

        return inventoryService.updateInventory(productId, quantity);
    }

    @PutMapping("/add/{productId}/{quantity}")
    public Inventory addToInventory(
            @PathVariable Long productId,
            @RequestBody Inventory inventory,
            @PathVariable Integer quantity) {

        return inventoryService.addToInventory(productId, inventory, quantity);
    }

    @DeleteMapping("/{productId}")
    public String deleteInventory(@PathVariable Long productId) {

        inventoryService.deleteInventory(productId);

        return "Inventory deleted successfully.";
    }


    @GetMapping("/status/{status}")
    public List<Inventory> getByStatus(
            @PathVariable InventoryStatus status) {

        return inventoryService.getInventoryByStatus(status);
    }


    @GetMapping("/search")
    public List<Inventory> searchProduct(
            @RequestParam String productName) {

        return inventoryService.searchByProductName(productName);
    }

    @PutMapping("/{productId}/reserve")
    public Inventory reserveStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        return inventoryService.reserveStock(productId, quantity);
    }

    @PutMapping("/{productId}/release")
    public Inventory releaseStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        return inventoryService.releaseStock(productId, quantity);
    }
}

