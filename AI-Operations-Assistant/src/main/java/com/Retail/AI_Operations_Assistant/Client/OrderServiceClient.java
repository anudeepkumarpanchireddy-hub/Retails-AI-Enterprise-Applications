package com.Retail.AI_Operations_Assistant.Client;



import com.Retail.AI_Operations_Assistant.DTO.FailedOrderAnalysis;
import com.Retail.AI_Operations_Assistant.Entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceClient {

    private final RestClient restClient;

    @Value("${services.order.url}")
    private String orderServiceUrl;

    public OrderServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }


    // Get Order By Id
    public Order getOrder(Long orderId) {
        return restClient.get()
                .uri(orderServiceUrl + "/orders/{orderId}", orderId)
                .retrieve()
                .body(Order.class);
    }

    // Get All Orders
    public List<Order> getAllOrders() {
        return restClient.get()
                .uri(orderServiceUrl + "/orders")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Order>>() {});
    }

    // Delete Order
    public void deleteOrder(Long orderId) {
        restClient.delete()
                .uri(orderServiceUrl + "/orders/{orderId}", orderId)
                .retrieve()
                .toBodilessEntity();
    }

    // Failed Orders
    public List<Order> getFailedOrders() {
        return restClient.get()
                .uri(orderServiceUrl + "/orders/failed")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Order>>() {});
    }

    // Today's Failed orders
    public List<Order> getTodaysFailedOrders(){
        return restClient.get()
                .uri(orderServiceUrl + "/failed/today")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Order>>() {});
    }

    // Failed Orders based on the Date
    public List<Order> getFailedOrdersByDate(LocalDate date){
        return restClient.get()
                .uri(orderServiceUrl + "/failed/{date}")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Order>>() {});
    }

    // Today's Orders
    public List<Order> getTodayOrders() {
        return restClient.get()
                .uri(orderServiceUrl + "/orders/today")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Order>>() {});
    }

    // Orders By Customer
    public List<Order> getCustomerOrders(Long customerId) {
        return restClient.get()
                .uri(orderServiceUrl + "/orders/customer/{customerId}", customerId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Order>>() {});
    }

    // Orders By Status
    public List<Order> getOrdersByStatus(String status) {
        return restClient.get()
                .uri(orderServiceUrl + "/orders/status/{status}", status)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Order>>() {});
    }

    // Revenue
    public BigDecimal getRevenue() {
        return restClient.get()
                .uri(orderServiceUrl + "/orders/revenue")
                .retrieve()
                .body(BigDecimal.class);
    }

    public FailedOrderAnalysis analyzeFailedOrder(Long orderId) {

        return restClient.get()
                .uri(orderServiceUrl + "/orders/failed/{orderId}/analysis", orderId)
                .retrieve()
                .body(FailedOrderAnalysis.class);
    }

}
