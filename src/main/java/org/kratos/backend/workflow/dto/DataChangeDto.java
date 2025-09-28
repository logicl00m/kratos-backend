package org.kratos.backend.workflow.dto;


import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

public record DataChangeDto(
		UUID id,
		Map<String, Object> diff,
		String currentState,
		UUID wfId,
		String createdBy,
		ZonedDateTime createdAt
) {

}