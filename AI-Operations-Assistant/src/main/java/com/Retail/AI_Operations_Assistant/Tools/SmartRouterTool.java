package com.Retail.AI_Operations_Assistant.Tools;

import com.Retail.AI_Operations_Assistant.Config.ToolRouterResponse;
import com.Retail.AI_Operations_Assistant.Router.ToolRouterService;
import lombok.RequiredArgsConstructor;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SmartRouterTool{

    private final ToolRouterService toolRouterService;

    @Tool(
            name = "routeRequest",
            description = "Routes the user's request to the appropriate enterprise tool."
    )
    public ToolRouterResponse routeRequest(
            @ToolParam(description = "User query")
            String userQuery) {

        return toolRouterService.route(userQuery);
    }
}
