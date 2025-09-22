package org.kratos.backend.workflow.service.impl;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.exceptions.BaseException;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State;
import org.kratos.backend.workflow.data.entities.Workflow;

import java.util.Objects;

@Getter
@Setter
@Builder
public class WorkflowContext {
	
	private final Workflow workflow;
	
	@Transactional
	public void transition(String action) {
		State currentStateCtx = this.getCurrentState();
		do {
			var stateHandler = currentStateCtx.kind()
			                                  .getHandler();
			if (Objects.isNull(stateHandler)) {
				throw BaseException.builder()
				                   .responseStatus(ResponseStatus.WF_STATE_HANDLER_NOT_DEFINED)
				                   .build();
			}
			stateHandler.transition(this, action);
			currentStateCtx = this.getCurrentState();
		} while (!currentStateCtx.kind().requiresExternalIntervention);
	}
	
	public State getCurrentState() {
		return this.workflow.getWfConfig()
		                    .context()
		                    .states()
		                    .get(this.workflow.getState());
	}
}
