package com.Retail.AI_Operations_Assistant.Executor;



public interface ToolExecutor {

    ToolExecutionResponse execute(
            ToolExecutionRequest request);

}