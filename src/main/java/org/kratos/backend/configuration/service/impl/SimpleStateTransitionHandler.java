package org.kratos.backend.configuration.service.impl;

import org.jetbrains.annotations.NotNull;
import org.kratos.backend.configuration.dto.TransitionResponse;
import org.kratos.backend.configuration.service.StateTransitionHandler;
import org.kratos.backend.workflow.service.impl.WorkflowContext;
import org.springframework.stereotype.Service;

@Service
public class SimpleStateTransitionHandler implements StateTransitionHandler {
	
	@Override
	public @NotNull TransitionResponse tryTransition(WorkflowContext workflowContext, String action) {
		return new TransitionResponse(false, null);
	}
}
