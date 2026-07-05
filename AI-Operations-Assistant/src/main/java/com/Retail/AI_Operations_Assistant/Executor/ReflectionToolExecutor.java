package com.Retail.AI_Operations_Assistant.Executor;

import com.Retail.AI_Operations_Assistant.Tools.AITool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReflectionToolExecutor implements ToolExecutor {

    private final List<AITool> toolBeans;

    public Method getMethod(String toolName) {

        for (AITool bean : toolBeans) {

            for (Method method : bean.getClass().getMethods()) {

                if (method.getName().equals(toolName)) {
                    return method;
                }
            }
        }

        throw new RuntimeException("Tool not found : " + toolName);
    }


    @Override
    public ToolExecutionResponse execute(
            ToolExecutionRequest request) {

        try {

            Method method = getMethod(request.getToolName());

            AITool targetBean = null;

            for (AITool bean : toolBeans) {

                if (method.getDeclaringClass().isAssignableFrom(bean.getClass())) {
                    targetBean = bean;
                    break;
                }
            }

            if (targetBean == null) {

                return ToolExecutionResponse.builder()
                        .success(false)
                        .error("Unable to locate tool bean.")
                        .build();
            }


            Object[] arguments =
                    buildArguments(method, request);

            Object result =
                    method.invoke(targetBean, arguments);

            return ToolExecutionResponse.builder()
                    .success(true)
                    .result(result)
                    .build();

        } catch (Exception ex) {

            return ToolExecutionResponse.builder()
                    .success(false)
                    .error(ex.getMessage())
                    .build();
        }
    }

    private Object[] buildArguments(
            Method method,
            ToolExecutionRequest request) {

        Parameter[] parameters = method.getParameters();

        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {

            args[i] =
                    request.getParameters()
                            .get(parameters[i].getName());
        }

        return args;
    }
}