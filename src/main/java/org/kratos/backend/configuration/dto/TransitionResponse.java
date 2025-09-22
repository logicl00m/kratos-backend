package org.kratos.backend.configuration.dto;

import org.kratos.backend.configuration.dto.ParsedWorkflowConfiguration.State;

public record TransitionResponse(
		boolean isSuccessful,
		State nextState
) {

}
