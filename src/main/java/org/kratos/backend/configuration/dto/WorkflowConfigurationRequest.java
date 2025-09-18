package org.kratos.backend.configuration.dto;

import jakarta.validation.constraints.NotBlank;

// fixme(low): add custom validation for the config using config schema
public record WorkflowConfigurationRequest(
		@NotBlank (message = "config must not be blank") String config) {
	
}