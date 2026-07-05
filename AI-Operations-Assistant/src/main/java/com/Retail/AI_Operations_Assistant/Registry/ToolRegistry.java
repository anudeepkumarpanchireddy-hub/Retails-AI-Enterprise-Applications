package com.Retail.AI_Operations_Assistant.Registry;

import com.Retail.AI_Operations_Assistant.DTO.ToolMetadata;
import com.Retail.AI_Operations_Assistant.IntentClassification.Intent;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class ToolRegistry {

    private final Map<Intent, List<ToolMetadata>> registry = new HashMap<>();

    @PostConstruct
    public void loadRegistry() {

        register(
                "getOrder",
                Intent.ORDER,
                "Retrieve an order using its order id",
                List.of("order","order details","order id","purchase"),
                List.of(
                        "Show me order 4000",
                        "Get order 5001",
                        "Retrieve order details"
                ),
                true,
                10
        );

        register(
                "deleteOrder",
                Intent.ORDER,
                "Delete an order using order id",
                List.of("delete","cancel","remove","order"),
                List.of(
                        "Delete order 4000",
                        "Remove order 1234"
                ),
                false,
                9
        );

        register(
                "getFailedOrders",
                Intent.ORDER,
                "Retrieve all failed orders",
                List.of("failed","orders","failure"),
                List.of(
                        "Show failed orders",
                        "List failed orders"
                ),
                true,
                10
        );

        register(
                "getTodaysFailedOrders",
                Intent.ORDER,
                "Retrieve today's failed orders",
                List.of("today","failed","orders"),
                List.of(
                        "Today's failed orders",
                        "Failed orders today"
                ),
                true,
                10
        );

        register(
                "getTodayOrders",
                Intent.ORDER,
                "Retrieve today's orders",
                List.of("today","orders"),
                List.of(
                        "Today's orders",
                        "Orders placed today"
                ),
                true,
                9
        );

        register(
                "getFailedOrdersByDate",
                Intent.ORDER,
                "Retrieve failed orders for a particular date",
                List.of("failed","date","orders"),
                List.of(
                        "Failed orders on 2026-07-02",
                        "Show failed orders yesterday"
                ),
                true,
                10
        );

        register(
                "getCustomerOrders",
                Intent.ORDER,
                "Retrieve all orders for a customer",
                List.of("customer","orders","history"),
                List.of(
                        "Show orders of customer 101",
                        "Customer purchase history"
                ),
                true,
                8
        );

        register(
                "getOrdersByStatus",
                Intent.ORDER,
                "Retrieve orders by status",
                List.of("status","confirmed","failed","cancelled"),
                List.of(
                        "Show confirmed orders",
                        "List cancelled orders"
                ),
                true,
                8
        );

        register(
                "getRevenue",
                Intent.ANALYTICS,
                "Calculate total revenue",
                List.of("revenue","sales","income","earnings"),
                List.of(
                        "Total revenue",
                        "Today's revenue",
                        "Sales report"
                ),
                true,
                7
        );

        // ======================= Inventory Tools =======================

        register(
                "getInventory",
                Intent.INVENTORY,
                "Retrieve inventory details using product id",
                List.of("inventory", "product", "stock", "product id"),
                List.of(
                        "Show inventory for product 1001",
                        "Get inventory of product 25",
                        "How much stock is available for product 10?"
                ),
                true,
                10
        );

        register(
                "getAllInventory",
                Intent.INVENTORY,
                "Retrieve complete inventory",
                List.of("inventory", "all inventory", "stock"),
                List.of(
                        "Show all inventory",
                        "List all products",
                        "Display inventory"
                ),
                true,
                9
        );

        register(
                "addInventory",
                Intent.INVENTORY,
                "Add stock to an existing inventory item",
                List.of("add inventory", "restock", "increase stock", "add stock"),
                List.of(
                        "Add 100 units to product 25",
                        "Restock product 1001",
                        "Increase inventory"
                ),
                false,
                8
        );

        register(
                "deleteInventory",
                Intent.INVENTORY,
                "Delete inventory for a product",
                List.of("delete inventory", "remove inventory"),
                List.of(
                        "Delete inventory for product 100",
                        "Remove inventory"
                ),
                false,
                7
        );

        register(
                "getInventoryByStatus",
                Intent.INVENTORY,
                "Retrieve inventory filtered by status",
                List.of("inventory status", "available", "out of stock", "low stock"),
                List.of(
                        "Show LOW_STOCK inventory",
                        "Show AVAILABLE inventory",
                        "List OUT_OF_STOCK products"
                ),
                true,
                9
        );

        register(
                "searchInventory",
                Intent.INVENTORY,
                "Search inventory using product name",
                List.of("search", "product", "inventory", "product name"),
                List.of(
                        "Search for Laptop",
                        "Find Mouse",
                        "Search inventory by name"
                ),
                true,
                9
        );

        register(
                "reserveInventory",
                Intent.INVENTORY,
                "Reserve stock for an order",
                List.of("reserve", "allocate", "inventory", "stock"),
                List.of(
                        "Reserve 5 laptops",
                        "Allocate stock for order",
                        "Reserve inventory"
                ),
                false,
                8
        );

        register(
                "releaseInventory",
                Intent.INVENTORY,
                "Release reserved inventory back to available stock",
                List.of("release", "reserved stock", "cancel reservation"),
                List.of(
                        "Release reserved inventory",
                        "Return reserved stock",
                        "Undo reservation"
                ),
                false,
                8
        );

        register(
                "getLowStockProducts",
                Intent.INVENTORY,
                "Retrieve products whose available stock is below a threshold",
                List.of(
                        "low stock",
                        "running low",
                        "replenish",
                        "restock",
                        "stock shortage",
                        "inventory below threshold"
                ),
                List.of(
                        "Which products are running low?",
                        "Show low stock products",
                        "Products below 10 units",
                        "Which products need replenishment?",
                        "List products to restock"
                ),
                true,
                10
        );
    }



    private void register(
            String toolName,
            Intent intent,
            String description,
            List<String> keywords,
            List<String> examples,
            boolean readOnly,
            int priority) {

        ToolMetadata tool = ToolMetadata.builder()
                                .toolName(toolName)
                                .description(description)
                                .intent(intent)
                                .keywords(keywords)
                                .examples(examples)
                                .readOnly(readOnly)
                                .priority(priority)
                                .build();

        registry.computeIfAbsent(intent, k -> new ArrayList<>())
                .add(tool);
    }

    public String createSearchDocument(ToolMetadata tool){

        return """
            Tool : %s
            
            Category : %s
            
            Description : %s
            
            Keywords : %s
            """
                .formatted(
                        tool.getToolName(),
                        tool.getIntent(),
                        tool.getDescription(),
                        String.join(",",tool.getKeywords())
                );
    }

    public List<ToolMetadata> getAllTools() {
        return registry.values()
                .stream()
                .flatMap(List::stream)
                .toList();
    }

    public ToolMetadata getTool(Intent intent) {
        return (ToolMetadata) registry.get(intent);
    }

    public List<ToolMetadata> getAllToolsFlat() {

        return registry.values()
                .stream()
                .flatMap(List::stream)
                .toList();
    }

    public List<ToolMetadata> getToolsByIntent(Intent intent) {

        return registry.getOrDefault(intent,
                Collections.emptyList());
    }

}
