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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/v1/client/private/workflow")
@RequiredArgsConstructor
public class WorkflowResource {
	
	private final WorkflowService workflowService;
	
	@PostMapping ("/create")
	public Response<WorkflowResponse> create(@RequestHeader ("X-Subject") String wfConfigId) {
		return Response.<WorkflowResponse>builder()
		               .data(workflowService.create(wfConfigId))
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/update")
	public Response<WorkflowResponse> update(@RequestHeader ("X-Subject") String wfConfigId,
	                                         @Valid @RequestBody Request<WorkflowUpdateRequest> workflowUpdateRequest) {
		return Response.<WorkflowResponse>builder()
		               .data(workflowService.update(wfConfigId, workflowUpdateRequest.data()))
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/get")
	public Response<WorkflowResponse> get(@RequestHeader ("X-Subject") String wfConfigId,
	                                      @Valid @RequestBody Request<String> workflowId) {
		return Response.<WorkflowResponse>builder()
		               .data(workflowService.get(workflowId))
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/get/all")
	public Response<List<WorkflowResponse>> getAll(@RequestHeader ("X-Subject") String wfConfigId) {
		PaginatedResponse<List<WorkflowResponse>> allWorkflows = workflowService.getAll(wfConfigId);
		return Response.<List<WorkflowResponse>>builder()
		               .data(allWorkflows.data())
		               .pagination(allWorkflows.pagination())
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
}
