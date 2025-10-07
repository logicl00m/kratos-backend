package org.kratos.backend.configuration.service.impl;

import org.jetbrains.annotations.NotNull;
import org.kratos.backend.configuration.dto.ScriptExecutionResponse;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.ExecutionSpec;
import org.kratos.backend.configuration.service.Executor;
import org.kratos.backend.workflow.service.impl.WorkflowContext;
import org.springframework.stereotype.Service;

@Service
public class InlineExecutor implements Executor {
	
	@Override
	public @NotNull ScriptExecutionResponse execute(WorkflowContext workflowContext, ExecutionSpec spec) {
		
		return null;
	}
	
	@Override
	public boolean validate(WorkflowContext workflowContext, ExecutionSpec spec) {
		return false;
	}
}
