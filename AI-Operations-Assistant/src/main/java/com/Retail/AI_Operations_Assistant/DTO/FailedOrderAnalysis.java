package com.Retail.AI_Operations_Assistant.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FailedOrderAnalysis {

    private Long orderId;

    private String status;

    private String rootCause;

    private String recommendation;
}
