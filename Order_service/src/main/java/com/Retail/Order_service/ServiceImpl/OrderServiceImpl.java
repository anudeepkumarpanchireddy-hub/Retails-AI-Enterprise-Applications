package com.Retail.Order_service.ServiceImpl;

import com.Retail.Order_service.DTO.FailedOrderAnalysis;
import com.Retail.Order_service.Entity.*;
import com.Retail.Order_service.ExternalUrl.InventoryClient;
import com.Retail.Order_service.ExternalUrl.PaymentClient;
import com.Retail.Order_service.ExternalUrl.UserClient;
import com.Retail.Order_service.Repository.OrderRepository;
import com.Retail.Order_service.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private UserClient userClient;

    @Override
    public Order createOrder(Order order) {

        Boolean authenticated = userClient.isAuthenticated(order.getUserId());

        if (!authenticated) {
            throw new RuntimeException("User is not authenticated.");
        }

        Inventory inventory = inventoryClient.getInventory(order.getProductId());
        inventoryClient.updateInventory(order.getProductId(), order.getQuantity());

        if (inventory == null) {
            order.setStatus(OrderStatus.FAILED);
            order.setFailureReason("Inventory record not found.");
            return orderRepository.save(order);
        }

        if (inventory.getAvailableQuantity() < order.getQuantity()) {
            order.setStatus(OrderStatus.FAILED);
            order.setFailureReason("Insufficient inventory.");
            return orderRepository.save(order);
        }

        order.setOrderId(generateOrderId());

        Payment payment = new Payment();
        payment.setOrderId(order.getOrderId());
        payment.setCustomerId(order.getCustomerId());
        payment.setAmount(order.getPrice());
        payment.setPaymentDate(LocalDateTime.now());

        // Random Payment Method
        PaymentMethod[] methods = PaymentMethod.values();
        PaymentMethod randomMethod = methods[new Random().nextInt(methods.length)];
        payment.setPaymentMethod(randomMethod);

        // Random Transaction Id
        payment.setTransactionId(UUID.randomUUID().toString());
        try {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            // Call Payment Service
            paymentClient.createPayment(payment);

            order.setStatus(OrderStatus.CONFIRMED);

        } catch (Exception ex) {

            payment.setPaymentStatus(PaymentStatus.FAILED);
            order.setStatus(OrderStatus.FAILED);
            order.setFailureReason(ex.getMessage());

        }
        return orderRepository.save(order);
    }


    private Long generateOrderId() {

        Long orderId;

        do {
            orderId = (long) ThreadLocalRandom.current().nextInt(1000, 10000);
        } while (orderRepository.existsByOrderId(orderId));

        return orderId;
    }


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderByOrderId(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id : " + orderId));
    }

    @Override
    public Order updateOrder(Long orderId, Order updatedOrder) {

        Order existingOrder = getOrderByOrderId(orderId);
        Inventory inventory =
                inventoryClient.getInventory(existingOrder.getProductId());

        inventoryClient.addToInventory(
                existingOrder.getProductId(),
                inventory,
                existingOrder.getQuantity());

        existingOrder.setProduct(updatedOrder.getProduct());
        existingOrder.setPrice(updatedOrder.getPrice());
        existingOrder.setProductId(updatedOrder.getProductId());
        existingOrder.setStatus(updatedOrder.getStatus());

        inventoryClient.updateInventory(updatedOrder.getProductId(),updatedOrder.getQuantity());


        return orderRepository.save(existingOrder);
    }

    @Override
    public void cancelOrder(Long orderId) {

        Order order = getOrderByOrderId(orderId);

        Inventory inventory =
                inventoryClient.getInventory(order.getProductId());

        //update the Inventory
        inventoryClient.updateInventory(order.getProductId(),order.getQuantity());


        // Soft Delete
        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        // If you want Hard Delete instead:
        // orderRepository.delete(order);
    }

    @Override
    public List<Order> getFailedOrders() {

        return orderRepository.findByStatus(OrderStatus.FAILED);

        // If your project doesn't have FAILED status,
        // replace FAILED with CANCELLED.
    }

    @Override
    public List<Order> getTodaysOrders() {

        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);

        return orderRepository.findByOrderDateBetween(start, end);
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public BigDecimal getRevenue() {
        return orderRepository.getTotalRevenue();
    }

    @Override
    public List<Order> getTodaysFailedOrders() {

        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);

        return orderRepository.findByStatusAndOrderDateBetween(
                OrderStatus.FAILED,
                start,
                end
        );
    }

    @Override
    public List<Order> getFailedOrdersByDate(LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        return orderRepository.findByStatusAndOrderDateBetween(
                OrderStatus.FAILED,
                start,
                end
        );
    }

    public FailedOrderAnalysis analyzeFailedOrder(Long orderId) {

        Order order = getOrderByOrderId(orderId);

        if (order.getStatus() != OrderStatus.FAILED) {

            return FailedOrderAnalysis.builder()
                    .orderId(orderId)
                    .status(order.getStatus().name())
                    .rootCause("Order is not failed.")
                    .recommendation("No action required.")
                    .build();
        }

        String recommendation;

        switch (order.getFailureReason()) {

            case "User authentication failed." ->
                    recommendation = "Ask the customer to login again.";

            case "Insufficient inventory." ->
                    recommendation = "Restock inventory or reduce quantity.";

            case "Inventory record not found." ->
                    recommendation = "Verify inventory service data.";

            default ->
                    recommendation = "Retry order or inspect application logs.";
        }

        return FailedOrderAnalysis.builder()
                .orderId(order.getOrderId())
                .status(order.getStatus().name())
                .rootCause(order.getFailureReason())
                .recommendation(recommendation)
                .build();
    }
}
