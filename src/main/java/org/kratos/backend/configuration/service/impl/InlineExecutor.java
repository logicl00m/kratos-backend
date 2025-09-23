package org.kratos.backend.configuration.service.impl;

import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.ExecutionSpec;
import org.kratos.backend.configuration.service.Executor;
import org.kratos.backend.workflow.service.impl.WorkflowContext;
import org.springframework.stereotype.Service;

@Service
public class InlineExecutor implements Executor {
	
	@Override
	public void execute(WorkflowContext workflowContext, ExecutionSpec spec) {
	
	}
	
	@Override
	public boolean validate(WorkflowContext workflowContext, ExecutionSpec spec) {
		return false;
	}
}
