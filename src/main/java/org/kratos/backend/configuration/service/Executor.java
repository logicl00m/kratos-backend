package org.kratos.backend.configuration.service;

import org.jetbrains.annotations.NotNull;
import org.kratos.backend.configuration.dto.ScriptExecutionResponse;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.ExecutionSpec;
import org.kratos.backend.workflow.service.impl.WorkflowContext;

public interface Executor {
	
	@NotNull ScriptExecutionResponse execute(WorkflowContext workflowContext, ExecutionSpec spec);
	
	boolean validate(WorkflowContext workflowContext, ExecutionSpec spec);
}
