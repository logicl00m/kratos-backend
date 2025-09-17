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
	
	// fixme(high): fix dto here
	@PostMapping ("/create")
	public Response<Void> run(@RequestHeader ("X-Subject") String wfConfigId
 
	) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/update")
	public Response<Void> update(@RequestHeader ("X-Subject") String wfConfigId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/get")
	public Response<Void> get(@RequestHeader ("X-Subject") String wfConfigId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/get/all")
	public Response<Void> getAll(@RequestHeader ("X-Subject") String wfConfigId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/get/history")
	public Response<Void> getHistory(@RequestHeader ("X-Subject") String wfConfigId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
}
