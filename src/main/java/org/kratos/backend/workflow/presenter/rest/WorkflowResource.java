package org.kratos.backend.workflow.presenter.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.dtos.PaginatedResponse;
import org.kratos.backend.common.dtos.Request;
import org.kratos.backend.common.dtos.Response;
import org.kratos.backend.workflow.dto.WorkflowResponse;
import org.kratos.backend.workflow.dto.WorkflowUpdateRequest;
import org.kratos.backend.workflow.service.WorkflowService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping ("/api/v1/client/private/workflow")
@RequiredArgsConstructor
public class WorkflowResource {
	
	private final WorkflowService workflowService;
	
	@PostMapping ("/create")
	public Response<WorkflowResponse> create(@RequestHeader ("X-Subject") UUID wfConfigId,
	                                         @AuthenticationPrincipal Jwt user) {
		return Response.<WorkflowResponse>builder()
		               .data(workflowService.create(wfConfigId, user.getSubject()))
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/update")
	public Response<WorkflowResponse> update(@RequestHeader ("X-Subject") UUID wfConfigId,
	                                         @Valid @RequestBody Request<WorkflowUpdateRequest> workflowUpdateRequest) {
		return Response.<WorkflowResponse>builder()
		               .data(workflowService.update(wfConfigId, workflowUpdateRequest.data()))
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/get")
	public Response<WorkflowResponse> get(@RequestHeader ("X-Subject") UUID wfConfigId,
	                                      @Valid @RequestBody Request<String> workflowId) {
		return Response.<WorkflowResponse>builder()
		               .data(workflowService.get(workflowId))
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/get/all")
	public Response<List<WorkflowResponse>> getAll(@RequestHeader ("X-Subject") UUID wfConfigId) {
		PaginatedResponse<List<WorkflowResponse>> allWorkflows = workflowService.getAll(wfConfigId);
		return Response.<List<WorkflowResponse>>builder()
		               .data(allWorkflows.data())
		               .pagination(allWorkflows.pagination())
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
}
