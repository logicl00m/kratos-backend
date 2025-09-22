package org.kratos.backend.configuration.service;

import org.jetbrains.annotations.NotNull;
import org.kratos.backend.workflow.service.impl.WorkflowContext;

public interface StateTransitionHandler {
	
	@NotNull
	String transition(WorkflowContext workflowContext, String action);
}
