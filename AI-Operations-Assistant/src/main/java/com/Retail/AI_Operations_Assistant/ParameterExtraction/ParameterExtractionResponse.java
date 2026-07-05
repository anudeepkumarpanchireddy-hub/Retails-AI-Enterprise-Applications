package com.Retail.AI_Operations_Assistant.ParameterExtraction;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParameterExtractionResponse {

    @Builder.Default
    private Map<String, Object> parameters = new HashMap<>();

    private boolean valid;

    private String message;
}
