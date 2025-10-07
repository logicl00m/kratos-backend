package org.kratos.backend.configuration.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.exceptions.BaseException;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.Execution;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.SimpleActionSpec;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.SimpleStateSpec;
import org.kratos.backend.configuration.service.StateTransitionHandler;
import org.kratos.backend.workflow.data.repositories.StateChangeRepository;
import org.kratos.backend.workflow.data.repositories.WorkflowRepository;
import org.kratos.backend.workflow.mapper.ChangeMapper;
import org.kratos.backend.workflow.service.impl.WorkflowContext;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SimpleStateTransitionHandler implements StateTransitionHandler {
	
	private final WorkflowRepository workflowRepository;
	private final StateChangeRepository stateChangeRepository;
	private final ChangeMapper changeMapper;
	
	@Transactional
	@Override
	public void transition(WorkflowContext workflowContext, String action, String userId) {
		
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
		if (Objects.nonNull(validation)) {
			boolean validated = validation
					.kind()
					.getHandler()
					.validate(workflowContext, validation.spec());
			
			if (!validated) {
				throw BaseException.builder()
				                   .responseStatus(ResponseStatus.ACTION_VALIDATION_FAILED)
				                   .build();
			}
		}
		
		var stateChange = changeMapper.stateChange(action, workflowContext.getWorkflow(), userId);
		stateChange.setToState(currentStateActionCtx.nextState());
		workflowContext.getWorkflow()
		               .setState(currentStateActionCtx.nextState());
		workflowRepository.save(workflowContext.getWorkflow());
		stateChangeRepository.save(stateChange);
		
		Execution operation = currentStateActionCtx.operation();
		if (Objects.nonNull(operation)) {
			operation.kind()
			         .getHandler()
			         .execute(workflowContext, operation.spec());
		}
	}
}
