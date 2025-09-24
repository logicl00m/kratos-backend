package org.kratos.backend.configuration.service.impl;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.dtos.Request;
import org.kratos.backend.common.exceptions.BaseException;
import org.kratos.backend.configuration.dto.RuntimeVersion;
import org.kratos.backend.configuration.dto.ScriptExecutionResponse;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.ExecutionSpec;
import org.kratos.backend.configuration.dto.WorkflowConfigurationContext.State.ScriptExecutionSpec;
import org.kratos.backend.configuration.presenter.api.ScriptApi;
import org.kratos.backend.configuration.dto.ScriptExecutionRequest;
import org.kratos.backend.configuration.service.Executor;
import org.kratos.backend.workflow.data.entities.Workflow;
import org.kratos.backend.workflow.mapper.WorkflowMapper;
import org.kratos.backend.workflow.service.impl.WorkflowContext;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScriptExecutor implements Executor {
	
	private final ScriptApi scriptApi;
	private final WorkflowMapper workflowMapper;
	
	@Override
	public @NotNull ScriptExecutionResponse execute(WorkflowContext workflowContext, ExecutionSpec spec) {
		var scriptExecutionSpec = (ScriptExecutionSpec) spec;
		Workflow workflow = workflowContext.getWorkflow();
		WorkflowConfigurationContext.Script script = workflow
				.getWfConfig()
				.context()
				.scripts()
				.get(scriptExecutionSpec.script());
		if (Objects.isNull(script)) {
			throw BaseException.builder()
			                   .responseStatus(ResponseStatus.SCRIPT_NOT_FOUND)
			                   .build();
		}
		var runtime = getRuntimeVersion(script.runtime());
		ScriptExecutionRequest request = new ScriptExecutionRequest(script.code(),
		                                                            scriptExecutionSpec.function(),
		                                                            workflowMapper.toDto(workflow));
		ScriptExecutionResponse response = scriptApi.execute(runtime.name(),
		                                                     runtime.version(),
		                                                     "script",
		                                                     new Request<>(request))
		                                            .getData();
		return response;
	}
	
	@Override
	public boolean validate(WorkflowContext workflowContext, ExecutionSpec spec) {
		ScriptExecutionResponse executionResponse = execute(workflowContext, spec);
		if (executionResponse.exitCode() == 0) {
			return Boolean.parseBoolean(executionResponse.stdout());
		}
		return false;
	}
	
	private RuntimeVersion getRuntimeVersion(String runtime) {
		String[] split = runtime.split(":");
		return new RuntimeVersion(split[0], split[1]);
	}
}
