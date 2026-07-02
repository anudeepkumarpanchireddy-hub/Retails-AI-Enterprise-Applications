package com.Retail.Order_service.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Column(nullable = false)
    private Long userId;

    @Id
    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDateTime.now();

        // Default status when an order is created
        if (this.status == null) {
            this.status = OrderStatus.PENDING;
        }
    }
}
