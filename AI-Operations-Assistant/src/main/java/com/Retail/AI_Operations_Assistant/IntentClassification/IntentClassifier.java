package com.Retail.AI_Operations_Assistant.IntentClassification;


import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IntentClassifier {

    private final Map<Intent, List<String>> keywords = Map.of(

            Intent.ORDER,
            List.of(
                    "order",
                    "purchase",
                    "cancel",
                    "failed order",
                    "customer order",
                    "today orders"
            ),

            Intent.INVENTORY,
            List.of(
                    "inventory",
                    "stock",
                    "product",
                    "restock",
                    "reserve",
                    "low stock",
                    "replenish"
            ),

            Intent.PAYMENT,
            List.of(
                    "payment",
                    "refund",
                    "transaction",
                    "credit card",
                    "upi",
                    "failed payment"
            ),

            Intent.USER,
            List.of(
                    "customer",
                    "user",
                    "login",
                    "authentication"
            ),

            Intent.ANALYTICS,
            List.of(
                    "revenue",
                    "sales",
                    "report",
                    "analytics",
                    "dashboard"
            )
    );

    public Intent classify(String query) {

        query = query.toLowerCase();

        for (Map.Entry<Intent, List<String>> entry : keywords.entrySet()) {

            boolean matched = entry.getValue()
                    .stream()
                    .anyMatch(query::contains);


            if (matched) {

                return entry.getKey();

            }
        }

        return Intent.UNKNOWN;
    }

}
