package org.kratos.backend.audit.presenter.rest;

import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.dtos.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/v1/common/private/activity")
@RequiredArgsConstructor
public class ActivityResource {
	
	@PostMapping ("/add")
	public Response<Void> addActivity(@RequestHeader ("X-Subject") String userId) {
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
}
