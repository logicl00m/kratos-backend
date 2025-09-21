package org.kratos.backend.configuration.presenter.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.dtos.*;
import org.kratos.backend.configuration.dto.WorkflowConfigurationRequest;
import org.kratos.backend.configuration.dto.WorkflowConfigurationResponse;
import org.kratos.backend.configuration.service.impl.WorkflowConfigurationServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping ("/api/v1")
@RequiredArgsConstructor
public class WorkflowConfigurationResource {
	
	private final WorkflowConfigurationServiceImpl wfConfigService;
	
	@PostMapping ("/client/private/configuration/create")
	public Response<WorkflowConfigurationResponse> create(@RequestHeader ("X-Subject") String userId,
	                                                      @Valid @RequestBody Request<WorkflowConfigurationRequest> request) {
		return Response.<WorkflowConfigurationResponse>builder()
		               .status(ResponseStatus.ALL_OK)
		               .data(wfConfigService.create(userId, request.data()))
		               .build();
	}
	
	@PostMapping ("/client/private/configuration/update")
	public Response<WorkflowConfigurationResponse> update(@RequestHeader ("X-Subject") UUID wfConfigId,
	                                                      @Valid @RequestBody Request<WorkflowConfigurationRequest> request,
	                                                      @AuthenticationPrincipal Jwt jwt
	) {
		return Response.<WorkflowConfigurationResponse>builder()
		               .status(ResponseStatus.ALL_OK)
		               .data(wfConfigService.update(wfConfigId, request.data(), jwt.getSubject()))
		               .build();
	}
	
	@PostMapping ("/client/private/configuration/get")
	public Response<WorkflowConfigurationResponse> get(@RequestHeader ("X-Subject") UUID wfConfigId) {
		return Response.<WorkflowConfigurationResponse>builder()
		               .status(ResponseStatus.ALL_OK)
		               .data(wfConfigService.get(wfConfigId))
		               .build();
	}
	
	@PostMapping ("/client/public/configuration/{version}/get/schema")
	public Response<Map<String, Object>> getSchema(@PathVariable String version) {
		return Response.<Map<String, Object>>builder()
		               .status(ResponseStatus.ALL_OK)
		               .data(wfConfigService.getSchema(version))
		               .build();
	}
	
	@PostMapping ("/common/private/configuration/get/all")
	public Response<List<WorkflowConfigurationResponse>> getAll(@RequestHeader ("X-Subject") String userId,
	                                                            @Valid @RequestBody PaginatedRequest<Void> request) {
		
		PaginatedResponse<List<WorkflowConfigurationResponse>> all = wfConfigService.getAll(userId,
		                                                                                    request.pagination());
		
		return Response.<List<WorkflowConfigurationResponse>>builder()
		               .status(ResponseStatus.ALL_OK)
		               .pagination(all.pagination())
		               .data(all.data())
		               .build();
	}
	
	@PostMapping ("/client/private/configuration/delete")
	public Response<Void> delete(@RequestHeader ("X-Subject") UUID wfConfigId) {
		wfConfigService.delete(wfConfigId);
		
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
}
