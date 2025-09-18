package org.kratos.backend.configuration.presenter.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.dtos.PaginatedResponse;
import org.kratos.backend.common.dtos.Request;
import org.kratos.backend.common.dtos.Response;
import org.kratos.backend.configuration.dto.WorkflowConfigurationRequest;
import org.kratos.backend.configuration.dto.WorkflowConfigurationResponse;
import org.kratos.backend.configuration.service.impl.WorkflowConfigurationServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping ("/api/v1/client/private/configuration")
@RequiredArgsConstructor
public class WorkflowConfigurationResource {
	
	private final WorkflowConfigurationServiceImpl workflowConfigurationService;
	
	@PostMapping ("/create")
	public Response<WorkflowConfigurationResponse> create(@RequestHeader ("X-Subject") String userId,
	                                                      @Valid @RequestBody Request<WorkflowConfigurationRequest> request) {
		return Response.<WorkflowConfigurationResponse>builder()
		               .status(ResponseStatus.ALL_OK)
		               .data(workflowConfigurationService.create(userId, request.data()))
		               .build();
	}
	
	@PostMapping ("/update")
	public Response<WorkflowConfigurationResponse> update(@RequestHeader ("X-Subject") UUID wfConfigId,
	                                                      @Valid @RequestBody Request<WorkflowConfigurationRequest> request
	) {
		return Response.<WorkflowConfigurationResponse>builder()
		               .status(ResponseStatus.ALL_OK)
		               .data(workflowConfigurationService.update(wfConfigId, request.data()))
		               .build();
	}
	
	@PostMapping ("/get")
	public Response<WorkflowConfigurationResponse> get(@RequestHeader ("X-Subject") UUID wfConfigId) {
		return Response.<WorkflowConfigurationResponse>builder()
		               .status(ResponseStatus.ALL_OK)
		               .data(workflowConfigurationService.get(wfConfigId))
		               .build();
	}
	
	@PostMapping ("/get/all")
	public Response<List<WorkflowConfigurationResponse>> getAll(@RequestHeader ("X-Subject") String userId) {
		
		PaginatedResponse<List<WorkflowConfigurationResponse>> all = workflowConfigurationService.getAll(userId);
		
		return Response.<List<WorkflowConfigurationResponse>>builder()
		               .status(ResponseStatus.ALL_OK)
		               .pagination(all.pagination())
		               .data(all.data())
		               .build();
	}
	
	@PostMapping ("/delete")
	public Response<Void> delete(@RequestHeader ("X-Subject") UUID wfConfigId) {
		workflowConfigurationService.delete(wfConfigId);
		
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
}
