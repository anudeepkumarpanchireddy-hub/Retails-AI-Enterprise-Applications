package com.Retail.AI_Operations_Assistant.Router;

import com.Retail.AI_Operations_Assistant.Config.ToolRouterResponse;
import com.Retail.AI_Operations_Assistant.DTO.ToolMetadata;

import com.Retail.AI_Operations_Assistant.Executor.ReflectionToolExecutor;
import com.Retail.AI_Operations_Assistant.Executor.ToolExecutionRequest;
import com.Retail.AI_Operations_Assistant.Executor.ToolExecutionResponse;
import com.Retail.AI_Operations_Assistant.IntentClassification.Intent;
import com.Retail.AI_Operations_Assistant.IntentClassification.IntentClassifier;

import com.Retail.AI_Operations_Assistant.ParameterExtraction.ParameterExtractionResponse;
import com.Retail.AI_Operations_Assistant.ParameterExtraction.ParameterExtractor;
import com.Retail.AI_Operations_Assistant.ParameterExtraction.ParameterValidator;
import com.Retail.AI_Operations_Assistant.Registry.ToolRegistry;
import com.Retail.AI_Operations_Assistant.SemanticToolSearch.SemanticToolSearch;
import com.Retail.AI_Operations_Assistant.SemanticToolSearch.ToolRanker;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToolRouterService {

    private final IntentClassifier intentClassifier;

    private final ToolRegistry toolRegistry;

    private final SemanticToolSearch semanticToolSearch;

    private final ToolRanker toolRanker;

    private final ParameterExtractor parameterExtractor;

    private final ParameterValidator parameterValidator;

    private final ReflectionToolExecutor toolExecutor;

    public ToolRouterResponse route(String userQuery) {

        /*
         * Step 1
         * Detect intent
         */
        Intent intent =
                intentClassifier.classify(userQuery);

        log.info("Intent = {}", intent);
        /*
         * Step 2
         * Candidate tools
         */
        List<ToolMetadata> candidateTools =
                toolRegistry.getToolsByIntent(intent);

        log.info("candidateTools = {}", candidateTools);

        if (candidateTools.isEmpty()) {

            throw new RuntimeException(
                    "No tools registered for intent : "
                            + intent);
        }

        /*
         * Step 3
         * Semantic Search
         */
        List<ToolMetadata> semanticMatches =
                semanticToolSearch.search(
                        userQuery,
                        candidateTools);
        log.info("semanticMatches = {}", semanticMatches);

        /*
         * Step 4
         * Business Ranking
         */
        List<ToolMetadata> rankedTools =
                toolRanker.rank(semanticMatches);

        if (rankedTools.isEmpty()) {

            throw new RuntimeException(
                    "No suitable tool found.");
        }

        log.info("rankedTools = {}", rankedTools);

        ToolMetadata selectedTool =
                rankedTools.getFirst();


        log.info("selectedTool = {}", selectedTool);
        /*
         * Step 5
         * Reflection Method
         */
        Method method =
                toolExecutor.getMethod(
                        selectedTool.getToolName());


        log.info("method = {}", method);
        /*
         * Step 6
         * Parameter Extraction
         */
        ParameterExtractionResponse extraction =
                parameterExtractor.extract(
                        userQuery,
                        method);

        log.info("extraction = {}", extraction);

        /*
         * Step 7
         * Validation
         */
        if (!parameterValidator.validate(extraction)) {

            throw new RuntimeException(
                    "Missing required parameters.");
        }

        /*
         * Step 8
         * Execute Tool
         */
        ToolExecutionResponse execution =
                toolExecutor.execute(
                        ToolExecutionRequest.builder()
                                .toolName(selectedTool.getToolName())
                                .parameters(extraction.getParameters())
                                .build());

        /*
         * Step 9
         * Response
         */
        return ToolRouterResponse.builder()
                .intent(intent)
                .candidateTools(rankedTools)
                .selectedTool(selectedTool)
                .extractedParameters(extraction)
                .executionResponse(execution)
                .userQuery(userQuery)
                .build();
    }
}