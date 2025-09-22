package org.kratos.backend.workflow.service.impl;

import jakarta.transaction.Transactional;
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
	
	@Transactional
	public void transition(String action) {
		State currentStateCtx = this.getCurrentState();
		do {
			var stateHandler = currentStateCtx.kind().handler;
			var transitionResponse = stateHandler.transition(this, action);
			this.workflow.setState(transitionResponse);
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
