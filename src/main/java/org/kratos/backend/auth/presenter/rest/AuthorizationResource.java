package org.kratos.backend.auth.presenter.rest;

import lombok.RequiredArgsConstructor;
import org.kratos.backend.auth.dto.AppRoleResponse;
import org.kratos.backend.auth.dto.ResourceOperation;
import org.kratos.backend.auth.service.ResourceOperationService;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.dtos.Response;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping ("/api/v1")
@RequiredArgsConstructor
public class AuthorizationResource {
	
	private final ResourceOperationService resourceOperationService;
	
	@PostMapping ("/common/private/subject/get/all")
	public Response<List<UUID>> getSubjects(@RequestHeader ("X-Subject") String userId) {
		return Response.<List<UUID>>builder()
		               .status(ResponseStatus.ALL_OK)
		               .data(resourceOperationService.getSubjects(userId))
		               .build();
	}
	
	@PostMapping ("/common/private/permission/get/by-subject")
	public Response<Set<ResourceOperation>> getPermissions(@RequestHeader ("X-Subject") UUID request,
	                                                       @AuthenticationPrincipal Jwt user) {
		return Response.<Set<ResourceOperation>>builder()
		               .status(ResponseStatus.ALL_OK)
		               .data(resourceOperationService.getResourceOperations(user.getSubject(),
		                                                                    request))
		               .build();
	}
	
	@PostMapping ("/common/private/roles/get/by-subject")
	public Response<Set<AppRoleResponse>> getRoles(@RequestHeader ("X-Subject") UUID request,
	                                               @AuthenticationPrincipal Jwt user) {
		return Response.<Set<AppRoleResponse>>builder()
		               .status(ResponseStatus.ALL_OK)
		               .data(resourceOperationService.getRoles(user.getSubject(),
		                                                       request))
		               .build();
	}
}
