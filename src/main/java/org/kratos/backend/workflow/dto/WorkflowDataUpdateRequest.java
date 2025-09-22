package org.kratos.backend.workflow.dto;

import java.util.Map;
import java.util.UUID;

public record WorkflowDataUpdateRequest(
		UUID id,
		Map<String, Object> data
) {
	
}
