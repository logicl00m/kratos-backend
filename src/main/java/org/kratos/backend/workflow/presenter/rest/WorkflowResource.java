package org.kratos.backend.workflow.presenter.rest;

import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.dtos.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/v1/client/private/workflow")
@RequiredArgsConstructor
public class WorkflowResource {
	
	@PostMapping ("/create")
	public Response<Void> runWorkflow(@RequestHeader ("X-Subject") String userId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/update")
	public Response<Void> updateWorkflow(@RequestHeader ("X-Subject") String userId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/get")
	public Response<Void> getWorkflow(@RequestHeader ("X-Subject") String userId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/get/all")
	public Response<Void> getAllWorkflows(@RequestHeader ("X-Subject") String userId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/history")
	public Response<Void> getHistory(@RequestHeader ("X-Subject") String userId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
}
