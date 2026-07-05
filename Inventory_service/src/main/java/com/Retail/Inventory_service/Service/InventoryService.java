package com.Retail.Inventory_service.Service;


import com.Retail.Inventory_service.Entity.Inventory;
import com.Retail.Inventory_service.Entity.InventoryStatus;

import java.util.List;

public interface InventoryService {

    Inventory createInventory(Inventory inventory);

    Inventory getInventoryByProductId(Long productId);

    List<Inventory> getAllInventory();

    List<Inventory> getLowStockProducts(Integer threshold);

    Inventory updateInventory(Long productId, Integer quantity);

    Inventory addToInventory(Long productId, Inventory inventory, Integer quantity);

    void deleteInventory(Long productId);

    List<Inventory> getInventoryByStatus(InventoryStatus status);

    List<Inventory> searchByProductName(String productName);

    Inventory reserveStock(Long productId, Integer quantity);

    Inventory releaseStock(Long productId, Integer quantity);
}
