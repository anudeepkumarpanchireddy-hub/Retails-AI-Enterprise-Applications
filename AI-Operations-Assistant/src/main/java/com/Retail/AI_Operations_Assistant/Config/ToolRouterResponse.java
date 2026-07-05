package com.Retail.AI_Operations_Assistant.Config;



import com.Retail.AI_Operations_Assistant.DTO.ToolMetadata;
import com.Retail.AI_Operations_Assistant.Executor.ToolExecutionResponse;
import com.Retail.AI_Operations_Assistant.IntentClassification.Intent;
import com.Retail.AI_Operations_Assistant.ParameterExtraction.ParameterExtractionResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolRouterResponse {
    private Intent intent;

    private String userQuery;

    private ToolMetadata selectedTool;

    private List<ToolMetadata> candidateTools;

    private ParameterExtractionResponse extractedParameters;

    private ToolExecutionResponse executionResponse;
}
