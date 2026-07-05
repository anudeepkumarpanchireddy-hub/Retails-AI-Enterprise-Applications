package com.Retail.Inventory_service.ServiceImpl;


import com.Retail.Inventory_service.Entity.Inventory;
import com.Retail.Inventory_service.Entity.InventoryStatus;
import com.Retail.Inventory_service.Repository.InventoryRepository;
import com.Retail.Inventory_service.Service.InventoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    @Override
    public Inventory createInventory(Inventory inventory) {
        return repository.save(inventory);
    }

    @Override
    public Inventory getInventoryByProductId(Long productId) {
        return repository.findByProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found"));
    }

    @Override
    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }

    @Override
    public List<Inventory> getLowStockProducts(Integer threshold) {

        return repository.findAll()
                .stream()
                .filter(item ->
                        (item.getAvailableQuantity() - item.getReservedQuantity())
                                <= threshold)
                .toList();
    }


    @Override
    public Inventory updateInventory(Long productId, Integer orderQuantity) {

        Inventory existing = getInventoryByProductId(productId);

        if (existing.getAvailableQuantity() < orderQuantity) {
            throw new RuntimeException("Insufficient inventory");
        }

        existing.setAvailableQuantity(
                existing.getAvailableQuantity() - orderQuantity);

        existing.setReservedQuantity(
                existing.getReservedQuantity() + orderQuantity);

        return repository.save(existing);
    }

    @Override
    public Inventory addToInventory(Long productId, Inventory inventory, Integer quantity) {

        Inventory existing = getInventoryByProductId(productId);


        existing.setAvailableQuantity(
                existing.getAvailableQuantity() + quantity);

        return repository.save(existing);
    }


    @Override
    public void deleteInventory(Long productId) {
        repository.deleteById(productId);
    }

    @Override
    public List<Inventory> getInventoryByStatus(InventoryStatus status) {
        return repository.findByStatus(status);
    }

    @Override
    public List<Inventory> searchByProductName(String productName) {
        return repository.findByProductNameContainingIgnoreCase(productName);
    }

    @Override
    public Inventory reserveStock(Long productId, Integer quantity) {

        Inventory inventory = getInventoryByProductId(productId);

        if (inventory.getAvailableQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock available.");
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - quantity);

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() + quantity);

        return repository.save(inventory);
    }

    @Override
    public Inventory releaseStock(Long productId, Integer quantity) {

        Inventory inventory = getInventoryByProductId(productId);

        if (inventory.getReservedQuantity() < quantity) {
            throw new RuntimeException("Reserved quantity is less than requested.");
        }

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() - quantity);

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() + quantity);

        return repository.save(inventory);
    }
}
