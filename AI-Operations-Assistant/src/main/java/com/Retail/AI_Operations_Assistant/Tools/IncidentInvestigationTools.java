package com.Retail.AI_Operations_Assistant.Tools;

import com.Retail.AI_Operations_Assistant.Client.OrderServiceClient;
import com.Retail.AI_Operations_Assistant.DTO.FailedOrderAnalysis;

import org.springframework.stereotype.Component;

@Component
public class IncidentInvestigationTools implements AITool{

    private final OrderServiceClient orderServiceClient;

    public IncidentInvestigationTools(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }

    public FailedOrderAnalysis getReasonOfFailedOrders(
            Long orderId) {
        return orderServiceClient.analyzeFailedOrder(orderId);
    }
}
