package com.Retail.AI_Operations_Assistant;

import com.Retail.AI_Operations_Assistant.Tools.IncidentInvestigationTools;
import com.Retail.AI_Operations_Assistant.Tools.InventoryTools;
import com.Retail.AI_Operations_Assistant.Tools.OrderTools;
import com.Retail.AI_Operations_Assistant.Tools.SmartRouterTool;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AiOperationsAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiOperationsAssistantApplication.class, args);
	}

	@Bean
	public List<ToolCallback> tools(SmartRouterTool smartRouterTool) {
		return List.of(ToolCallbacks.from(smartRouterTool));
	}


//	@Bean
//	public List<ToolCallback> tools(
//			SmartRouterTool smartRouterTool
////			InventoryTools inventoryTools,
////			OrderTools orderTools,
////			IncidentInvestigationTools incidentInvestigationTools
//	) {
//
//		List<ToolCallback> callbacks = new ArrayList<>();
//
////		callbacks.addAll(Arrays.asList(ToolCallbacks.from(inventoryTools)));
////		callbacks.addAll(Arrays.asList(ToolCallbacks.from(orderTools)));
////		callbacks.addAll(Arrays.asList(ToolCallbacks.from(incidentInvestigationTools)));
//
//		callbacks.addAll(Arrays.asList(ToolCallbacks.from(smartRouterTool)));
//
//		return callbacks;
//	}

}
