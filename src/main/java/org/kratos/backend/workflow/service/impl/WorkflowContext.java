package org.kratos.backend.workflow.service.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.kratos.backend.configuration.dto.ParsedWorkflowConfiguration.State;
import org.kratos.backend.workflow.data.entities.Workflow;
import org.kratos.backend.workflow.data.repositories.WorkflowRepository;

@Getter
@Setter
@Builder
public class WorkflowContext {
	
	private final Workflow workflow;
	private final WorkflowRepository workflowRepository;
	
	public void tryTransition(String action) {
		State currentStateCtx = this.getCurrentState();
		do {
			var stateHandler = currentStateCtx.kind().handler;
			var transitionResponse = stateHandler.tryTransition(this, action);
			if (transitionResponse.isSuccessful()) {
				this.workflow.setState(transitionResponse.nextState()
				                                         .name());
			}
			workflowRepository.saveAndFlush(workflow);
			currentStateCtx = this.getCurrentState();
		} while (!currentStateCtx.kind().requiresExternalIntervention);
	}
	
	public State getCurrentState() {
		return this.workflow.getWfConfig()
		                    .parsed()
		                    .states()
		                    .allStates()
		                    .get(this.workflow.getState());
	}
}
