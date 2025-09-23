package org.kratos.backend.configuration.dto;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.Executors;
import org.kratos.backend.configuration.service.impl.InlineExecutor;
import org.kratos.backend.configuration.service.impl.ScriptExecutor;
import org.kratos.backend.workflow.service.impl.WorkflowContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExecutorRegistry {
	
	private final InlineExecutor inlineExecutor;
	private final ScriptExecutor scriptExecutor;
	
	@PostConstruct
	public void init() {
		Executors.INLINE.handler = inlineExecutor;
		Executors.SCRIPT.handler = scriptExecutor;
	}
	
}
