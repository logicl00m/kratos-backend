package org.kratos.backend.configuration.service;

import org.kratos.backend.workflow.service.impl.WorkflowContext;

public interface StateTransitionHandler {
	
	void transition(WorkflowContext workflowContext, String action, String userId);
}
