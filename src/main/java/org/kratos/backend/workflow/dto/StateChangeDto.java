package org.kratos.backend.workflow.dto;


import java.time.ZonedDateTime;
import java.util.UUID;

public record StateChangeDto(
		UUID id,
		String fromState,
		String toState,
		String action,
		UUID wfId,
		String createdBy,
		ZonedDateTime createdAt
) {

}
