package com.Retail.Order_service.Repository;


import com.Retail.Order_service.Entity.Order;
import com.Retail.Order_service.Entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Get all orders of a customer.
     */
    List<Order> findByCustomerId(Long customerId);

    boolean existsByOrderId(Long orderId);

    /**
     * Get all orders with a particular status.
     */
    List<Order> findByStatus(OrderStatus status);

    /**
     * Get today's orders.
     */
    List<Order> findByOrderDateBetween(LocalDateTime startOfDay,
                                       LocalDateTime endOfDay);

    /**
     * Calculate total revenue from delivered orders.
     */
    @Query("""
            SELECT COALESCE(SUM(o.price),0)
            FROM Order o
            WHERE o.status = 'DELIVERED'
            """)
    BigDecimal getTotalRevenue();


    List<Order> findByStatusAndOrderDateBetween(
            OrderStatus status,
            LocalDateTime start,
            LocalDateTime end
    );
}
