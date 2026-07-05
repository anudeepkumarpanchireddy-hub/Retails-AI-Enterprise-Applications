package com.Retail.AI_Operations_Assistant.DTO;

import com.Retail.AI_Operations_Assistant.IntentClassification.Intent;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolMetadata {

    private String toolName;

    private String domain;

    private Intent intent;

    private String description;

    private List<String> keywords;

    private List<String> examples;

    private boolean readOnly;

    private int priority;
}
