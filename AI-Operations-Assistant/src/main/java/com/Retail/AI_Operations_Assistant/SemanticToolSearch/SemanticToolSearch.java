package com.Retail.AI_Operations_Assistant.SemanticToolSearch;

import com.Retail.AI_Operations_Assistant.DTO.ToolMetadata;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;




@Service
@RequiredArgsConstructor
public class SemanticToolSearch {

    public List<ToolMetadata> search(
            String question,
            List<ToolMetadata> candidateTools) {

        String query = question.toLowerCase();


        return candidateTools.stream()
                .sorted(Comparator.comparingInt(
                        tool -> -calculateScore(query, tool)))
                .limit(5)
                .toList();
    }

    private int calculateScore(
            String query,
            ToolMetadata tool) {

        int score = 0;

        // Description Match
        if (tool.getDescription() != null &&
                query.contains(tool.getDescription().toLowerCase())) {
            score += 5;
        }

        // Keyword Match
        for (String keyword : tool.getKeywords()) {

            if (query.contains(keyword.toLowerCase())) {
                score += 10;
            }
        }


        return score;
    }
}
