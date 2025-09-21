package org.kratos.backend.configuration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

public record WorkflowConfigurationResponse(
		@NotBlank
		UUID id,
		
		@NotBlank (message = "config must not be blank")
		Map<String, Object> config,
		
		@NotBlank
		String createdBy,
		
		@NotBlank
		String updatedBy,
		
		@NotNull
		ZonedDateTime createdAt,
		
		@NotNull
		ZonedDateTime updatedAt,
		
		@NotNull
		Boolean isActive
) {
	
}