package com.Retail.AI_Operations_Assistant.Tools;


import com.Retail.AI_Operations_Assistant.Client.OrderServiceClient;
import com.Retail.AI_Operations_Assistant.Entity.Order;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class OrderTools implements AITool{

    private final OrderServiceClient orderServiceClient;

    public OrderTools(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }



    public Order getOrder(
            Long id) {

        return orderServiceClient.getOrder(id);
    }



    public String deleteOrder(
            Long orderId) {

        orderServiceClient.deleteOrder(orderId);
        return "Order deleted successfully with OrderId: " + orderId;
    }


    public List<Order> getFailedOrders() {
        return orderServiceClient.getFailedOrders();
    }


    public List<Order> getTodaysFailedOrders() {
        return orderServiceClient.getTodaysFailedOrders();
    }


    public List<Order> getTodayOrders() {
        return orderServiceClient.getTodayOrders();
    }


    public List<Order> getFailedOrdersByDate(
                                             LocalDate date) {
        return orderServiceClient.getFailedOrdersByDate(date);
    }


    public List<Order> getCustomerOrders(
            Long customerId) {

        return orderServiceClient.getCustomerOrders(customerId);
    }


    public List<Order> getOrdersByStatus(
            String status) {

        return orderServiceClient.getOrdersByStatus(status);
    }


    public BigDecimal getRevenue() {
        return orderServiceClient.getRevenue();
    }

}
