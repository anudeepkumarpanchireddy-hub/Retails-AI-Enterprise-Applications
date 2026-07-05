package com.Retail.AI_Operations_Assistant.ParameterExtraction;

import com.Retail.AI_Operations_Assistant.Registry.ToolRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ParameterExtractor {

    private final ToolRegistry toolRegistry;

    private static final Pattern NUMBER =
            Pattern.compile("\\d+");

    private static final Pattern DATE =
            Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    public ParameterExtractionResponse extract(
            String userQuery,
            Method toolMethod) {

        Map<String, Object> extracted = new HashMap<>();

        Parameter[] parameters = toolMethod.getParameters();

        for (Parameter parameter : parameters) {

            Class<?> type = parameter.getType();

            String parameterName = parameter.getName();

            Object value = extractValue(type, userQuery);

            extracted.put(parameterName, value);
        }

        return ParameterExtractionResponse.builder()
                .parameters(extracted)
                .valid(true)
                .message("Parameters extracted successfully.")
                .build();
    }

    private Object extractValue(
            Class<?> type,
            String query) {

        if (type.equals(Long.class) || type.equals(long.class)) {

            Matcher matcher = NUMBER.matcher(query);

            if (matcher.find()) {
                return Long.parseLong(matcher.group());
            }
        }

        if (type.equals(Integer.class) || type.equals(int.class)) {

            Matcher matcher = NUMBER.matcher(query);

            if (matcher.find()) {
                return Integer.parseInt(matcher.group());
            }
        }

        if (type.equals(LocalDate.class)) {

            Matcher matcher = DATE.matcher(query);

            if (matcher.find()) {
                return LocalDate.parse(matcher.group());
            }
        }

        if (type.equals(String.class)) {

            return query;
        }

        return null;
    }

}
