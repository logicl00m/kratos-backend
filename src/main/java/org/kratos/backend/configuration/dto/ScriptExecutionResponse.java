package org.kratos.backend.configuration.dto;

public record ScriptExecutionResponse(
		Integer exitCode,
		String stdout,
		String stderr
) {
	
}
