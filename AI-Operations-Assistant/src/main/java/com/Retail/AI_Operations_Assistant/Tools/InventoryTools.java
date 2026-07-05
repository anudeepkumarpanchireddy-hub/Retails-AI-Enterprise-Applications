package com.Retail.AI_Operations_Assistant.Tools;

import com.Retail.AI_Operations_Assistant.Client.InventoryServiceClient;
import com.Retail.AI_Operations_Assistant.Entity.Inventory;
//import org.springframework.ai.tool.annotation.Tool;
//import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryTools implements AITool{

    private final InventoryServiceClient inventoryServiceClient;

    public InventoryTools(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }



    public Inventory getInventory(
            Long productId) {

        return inventoryServiceClient.getInventory(productId);
    }


    public List<Inventory> getAllInventory() {
        return inventoryServiceClient.getAllInventory();
    }



    public Inventory addInventory(
            Long productId,
            Integer quantity) {

        return inventoryServiceClient.addInventory(productId, quantity);
    }


    public String deleteInventory(
            Long productId) {

        inventoryServiceClient.deleteInventory(productId);
        return "Inventory deleted successfully.";
    }

    public List<Inventory> getInventoryByStatus(
            String status) {

        return inventoryServiceClient.getInventoryByStatus(status);
    }


    public List<Inventory> searchInventory(
            String productName) {

        return inventoryServiceClient.searchInventory(productName);
    }


    public Inventory reserveInventory(
            Long productId,
            Integer quantity) {

        return inventoryServiceClient.reserveInventory(productId, quantity);
    }


    public Inventory releaseInventory(
            Long productId,
            Integer quantity) {

        return inventoryServiceClient.releaseInventory(productId, quantity);
    }



    public String getLowStockProducts(
            Integer threshold) {

        if (threshold == null) {
            threshold = 10;
        }

        List<Inventory> products =
                inventoryServiceClient.getLowStockProducts(threshold);

        if (products.isEmpty()) {
            return "No products are running low on stock.";
        }

        StringBuilder response = new StringBuilder();

        response.append("Low Stock Report\n\n");
        response.append("Products below ")
                .append(threshold)
                .append(" units:\n\n");

        for (Inventory inventory : products) {

            response.append("Product ID : ")
                    .append(inventory.getProductId())
                    .append("\n");

            response.append("Product Name : ")
                    .append(inventory.getProductName())
                    .append("\n");

            response.append("Available Quantity : ")
                    .append(inventory.getAvailableQuantity())
                    .append("\n");

            response.append("Reserved Quantity : ")
                    .append(inventory.getReservedQuantity())
                    .append("\n");

            response.append("Status : ")
                    .append(inventory.getStatus())
                    .append("\n");

            if (inventory.getAvailableQuantity() <= 5) {
                response.append("Recommendation : Restock Immediately\n");
            } else {
                response.append("Recommendation : Schedule replenishment soon\n");
            }

            response.append("----------------------------------\n");
        }

        return response.toString();
    }

}
