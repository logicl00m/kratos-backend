package org.kratos.backend.configuration.service.impl;

import org.kratos.backend.configuration.dto.WorkflowConfigurationContext;
import org.kratos.backend.configuration.service.Executor;
import org.kratos.backend.workflow.service.impl.WorkflowContext;
import org.springframework.stereotype.Service;

@Service
public class ScriptExecutor implements Executor {
	
	@Override
	public void execute(WorkflowContext workflowContext, WorkflowConfigurationContext.State.ExecutionSpec spec) {
	
	}
	
	@Override
	public boolean validate(WorkflowContext workflowContext, WorkflowConfigurationContext.State.ExecutionSpec spec) {
		return false;
	}
}
