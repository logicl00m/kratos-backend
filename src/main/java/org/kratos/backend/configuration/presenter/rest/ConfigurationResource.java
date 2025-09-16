package org.kratos.backend.configuration.presenter.rest;

import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.dtos.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// todo: Abir
@RestController
@RequestMapping ("/api/v1/client/private/configuration")
@RequiredArgsConstructor
public class ConfigurationResource {
	
	@PostMapping ("/create")
	public Response<Void> createConfiguration(@RequestHeader ("X-Subject") String userId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/update")
	public Response<Void> updateConfiguration(@RequestHeader ("X-Subject") String workflowId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/get")
	public Response<Void> getConfiguration(@RequestHeader ("X-Subject") String workflowId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/get/all")
	public Response<Void> getAllConfigurations(@RequestHeader ("X-Subject") String userId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/delete")
	public Response<Void> deleteConfiguration(@RequestHeader ("X-Subject") String configurationId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
}
