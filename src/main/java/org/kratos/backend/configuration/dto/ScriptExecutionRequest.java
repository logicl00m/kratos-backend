package org.kratos.backend.configuration.dto;

public record ScriptExecutionRequest(
		String script,
		String function,
		Object input
) {

}
