package org.kratos.backend.configuration.dto;

import jakarta.validation.constraints.NotNull;
import org.kratos.backend.common.validators.ValidJsonSchema;

import java.util.Map;

public record WorkflowConfigurationRequest(
		@NotNull
		@ValidJsonSchema (schema = "/schemas/configuration.schema.json")
		Map<String, Object> config
) {
	
}