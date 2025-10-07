package org.kratos.backend.configuration.presenter.api;

import org.kratos.backend.common.dtos.Request;
import org.kratos.backend.common.dtos.Response;
import org.kratos.backend.configuration.dto.ScriptExecutionRequest;
import org.kratos.backend.configuration.dto.ScriptExecutionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient (name = "script-gateway", url = "${scripts.executor.gateway}")
public interface ScriptApi {
	
	@GetMapping ("/health")
	String health();
	
	@PostMapping ("/{runtime}/{version}/{kind}/run")
	Response<ScriptExecutionResponse> execute(@PathVariable ("runtime") String runtime,
	                                          @PathVariable ("version") String version,
	                                          @PathVariable ("kind") String kind,
	                                          @RequestBody Request<ScriptExecutionRequest> request);
}
