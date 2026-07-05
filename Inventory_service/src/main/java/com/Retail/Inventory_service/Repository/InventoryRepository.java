package com.Retail.Inventory_service.Repository;


import com.Retail.Inventory_service.Entity.Inventory;
import com.Retail.Inventory_service.Entity.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long productId);

    List<Inventory> findByStatus(InventoryStatus status);

    List<Inventory> findByProductNameContainingIgnoreCase(String productName);

    List<Inventory> findByAvailableQuantityLessThanEqual(Integer threshold);


}
