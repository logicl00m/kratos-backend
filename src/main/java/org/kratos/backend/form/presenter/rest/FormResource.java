package org.kratos.backend.form.presenter.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.dtos.PaginatedRequest;
import org.kratos.backend.common.dtos.Request;
import org.kratos.backend.common.dtos.Response;
import org.kratos.backend.form.dto.FormCreateRequest;
import org.kratos.backend.form.dto.FormUpdateRequest;
import org.kratos.backend.form.dto.FormResponse;
import org.kratos.backend.form.service.FormService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping ("/api/v1/common/private/form")
@RequiredArgsConstructor
public class FormResource {
	
	private final FormService formService;
	
	@PostMapping ("/create")
	public Response<FormResponse> create(@AuthenticationPrincipal Jwt user,
	                                     @Valid @RequestBody Request<FormCreateRequest> request) {
		return Response.<FormResponse>builder()
		               .data(formService.create(user.getSubject(), request.data()))
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/update")
	public Response<FormResponse> update(@AuthenticationPrincipal Jwt user,
	                                     @Valid @RequestBody Request<FormUpdateRequest> request) {
		return Response.<FormResponse>builder()
		               .data(formService.update(user.getSubject(), request.data()))
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/get")
	public Response<FormResponse> get(@Valid @RequestBody Request<UUID> formId) {
		return Response.<FormResponse>builder()
		               .data(formService.get(formId.data()))
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/get/all")
	public Response<List<FormResponse>> getAll(@RequestBody PaginatedRequest<Void> request) {
		var forms = formService.getAll(request.pagination());
		
		return Response.<List<FormResponse>>builder()
		               .data(forms.data())
		               .pagination(forms.pagination())
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
	
	@PostMapping ("/delete")
	public Response<Void> delete(@AuthenticationPrincipal Jwt user, @Valid @RequestBody Request<UUID> formId) {
		formService.delete(user.getSubject(), formId.data());
		
		return Response.<Void>builder()
		               .status(ResponseStatus.ALL_OK)
		               .build();
	}
}
