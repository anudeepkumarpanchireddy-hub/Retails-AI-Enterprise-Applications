package com.Retail.AI_Operations_Assistant.ParameterExtraction;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ParameterValidator {

    public boolean validate(ParameterExtractionResponse response){

        return response.getParameters()
                .values()
                .stream()
                .noneMatch(Objects::isNull);

    }

}
