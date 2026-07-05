package com.Retail.AI_Operations_Assistant.Executor;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolExecutionResponse {

    private boolean success;

    private Object result;

    private String error;

}
