package org.kratos.backend.configuration.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record WorkflowConfigurationResponse(
		@NotBlank UUID id,
		@NotBlank (message = "config must not be blank") String config) {
	
}