package org.kratos.backend.configuration.service;

import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.ExecutionSpec;
import org.kratos.backend.workflow.service.impl.WorkflowContext;

public interface Executor {
	
	void execute(WorkflowContext workflowContext, ExecutionSpec spec);
	
	boolean validate(WorkflowContext workflowContext, ExecutionSpec spec);
}
