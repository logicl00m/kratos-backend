package org.kratos.backend.workflow.dto;

import java.util.UUID;

public record WorkflowUpdateRequest(
		UUID id,
		String action
) {

}
