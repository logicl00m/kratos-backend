package org.kratos.backend.configuration.dto;

import org.kratos.backend.common.validators.ValidJsonSchema;

import java.util.Map;

public record WorkflowConfigurationRequest(
		@ValidJsonSchema (schema = "/schemas/v1.configuration.schema.json")
		Map<String, Object> config
) {
	
}