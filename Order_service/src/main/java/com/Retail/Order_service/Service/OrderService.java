package com.Retail.Order_service.Service;

import com.Retail.Order_service.Entity.Order;
import com.Retail.Order_service.Entity.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    Order getOrderByOrderId(Long orderId);

    List<Order> getAllOrders();

    Order updateOrder(Long id, Order order);

    void cancelOrder(Long id);

    List<Order> getFailedOrders();

    List<Order> getTodaysOrders();

    List<Order> getOrdersByCustomerId(Long customerId);


    List<Order> getOrdersByStatus(OrderStatus status);

    BigDecimal getRevenue();
}
