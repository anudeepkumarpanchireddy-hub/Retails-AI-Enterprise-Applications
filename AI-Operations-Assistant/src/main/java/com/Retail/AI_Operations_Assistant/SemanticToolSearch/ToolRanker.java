package com.Retail.AI_Operations_Assistant.SemanticToolSearch;


import com.Retail.AI_Operations_Assistant.DTO.ToolMetadata;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ToolRanker {

    public List<ToolMetadata> rank(List<ToolMetadata> tools){


        return tools.stream()

                .sorted(
                        Comparator
                                .comparingInt(ToolMetadata::getPriority)
                                .reversed())

                .limit(3)
                .toList();
    }

}
