package org.kratos.backend.configuration.service;

import org.jetbrains.annotations.NotNull;
import org.kratos.backend.configuration.dto.TransitionResponse;
import org.kratos.backend.workflow.service.impl.WorkflowContext;

public interface StateTransitionHandler {
	
	@NotNull
	TransitionResponse tryTransition(WorkflowContext workflowContext, String action);
}
