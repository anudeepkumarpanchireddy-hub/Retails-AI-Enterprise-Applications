package com.Retail.Order_service.Controller;

import com.Retail.Order_service.DTO.FailedOrderAnalysis;
import com.Retail.Order_service.Entity.Order;
import com.Retail.Order_service.Entity.OrderStatus;
import com.Retail.Order_service.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderService orderService;

    /**
     * Create Order
     * POST /orders
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    /**
     * Get Order by Id
     * GET /orders/{id}
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderByOrderId(orderId));
    }

    /**
     * Get All Orders
     * GET /orders
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * Update Order
     * PUT /orders/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id,
                                             @RequestBody Order order) {

        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }

    /**
     * Cancel Order
     * DELETE /orders/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {

        orderService.cancelOrder(id);

        return ResponseEntity.ok("Order cancelled successfully.");
    }

    /**
     * Failed Orders
     * GET /orders/failed
     */
    @GetMapping("/failed")
    public ResponseEntity<List<Order>> getFailedOrders() {

        return ResponseEntity.ok(orderService.getFailedOrders());
    }

    /**
     * Today's Orders
     * GET /orders/today
     */
    @GetMapping("/today")
    public ResponseEntity<List<Order>> getTodaysOrders() {

        return ResponseEntity.ok(orderService.getTodaysOrders());
    }

    @GetMapping("/failed/today")
    public List<Order> getTodaysFailedOrders() {
        return orderService.getTodaysFailedOrders();
    }

    @GetMapping("/failed/date")
    public List<Order> getFailedOrdersByDate(
            @RequestParam LocalDate date) {

        return orderService.getFailedOrdersByDate(date);
    }

    /**
     * Orders by Customer
     * GET /orders/customer/{customerId}
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    /**
     * Orders by Status
     * GET /orders/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(
            @PathVariable OrderStatus status) {

        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    /**
     * Revenue Report
     * GET /orders/revenue
     */
    @GetMapping("/revenue")
    public ResponseEntity<BigDecimal> getRevenue() {
        return ResponseEntity.ok(orderService.getRevenue());
    }

    @GetMapping("/failed/{orderId}/analysis")
    public FailedOrderAnalysis analyzeFailedOrder(
            @PathVariable Long orderId) {

        return orderService.analyzeFailedOrder(orderId);
    }
}
