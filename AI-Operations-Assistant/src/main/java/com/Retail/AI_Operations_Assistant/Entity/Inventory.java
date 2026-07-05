package com.Retail.AI_Operations_Assistant.Entity;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    private Long productId;

    private String productName;

    private Integer availableQuantity;

    private Integer reservedQuantity;

    private Integer reorderLevel;

    private InventoryStatus status;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
