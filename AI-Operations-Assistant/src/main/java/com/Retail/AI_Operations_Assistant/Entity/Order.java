package com.Retail.AI_Operations_Assistant.Entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {


    private Long userId;

    private Long orderId;

    private Long productId;

    private Long customerId;

    private Integer quantity;

    private String product;

    private BigDecimal price;

    private OrderStatus status;

    private LocalDateTime orderDate;

}
