package com.Retail.AI_Operations_Assistant.Executor;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolExecutionRequest {

    private String toolName;

    private Map<String, Object> parameters;

}
