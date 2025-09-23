package org.kratos.backend.configuration.service.impl;

import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.exceptions.BaseException;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.Execution;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.SimpleActionSpec;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.SimpleStateSpec;
import org.kratos.backend.configuration.service.StateTransitionHandler;
import org.kratos.backend.workflow.data.repositories.WorkflowRepository;
import org.kratos.backend.workflow.service.impl.WorkflowContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleStateTransitionHandler implements StateTransitionHandler {
	
	private final WorkflowRepository workflowRepository;
	
	@Override
	public void transition(WorkflowContext workflowContext, String action) {
		
		var currentStateCtx = workflowContext.getCurrentState();
		var currentStateSpec = (SimpleStateSpec) currentStateCtx.spec();
		SimpleActionSpec currentStateActionCtx = currentStateSpec.actions()
		                                                         .get(action);
		
		if (currentStateActionCtx == null) {
			throw BaseException.builder()
			                   .responseStatus(ResponseStatus.ACTION_NOT_FOUND_FOR_CURRENT_STATE)
			                   .build();
		}
		
		Execution validation = currentStateActionCtx.validation();
		boolean validated = validation
				.kind()
				.getHandler()
				.validate(workflowContext, validation.spec());
		
		if (!validated) {
			throw BaseException.builder()
			                   .responseStatus(ResponseStatus.ACTION_VALIDATION_FAILED)
			                   .build();
		}
		
		workflowContext.getWorkflow()
		               .setState(currentStateActionCtx.nextState());
		workflowRepository.saveAndFlush(workflowContext.getWorkflow());
		
		Execution operation = currentStateActionCtx.operation();
		operation.kind()
		         .getHandler()
		         .execute(workflowContext, operation.spec());
	}
}
