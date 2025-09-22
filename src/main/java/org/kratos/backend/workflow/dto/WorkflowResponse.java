package org.kratos.backend.workflow.dto;

import org.kratos.backend.configuration.data.entities.WorkflowConfiguration;

import java.util.Map;
import java.util.UUID;

public record WorkflowResponse(
		UUID id,
		WorkflowConfiguration wfConfig,
		Map<String, Object> data,
		String state,
		boolean isArchived
) {
	
}
