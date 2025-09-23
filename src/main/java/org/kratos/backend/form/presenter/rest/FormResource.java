package org.kratos.backend.form.presenter.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.dtos.Request;
import org.kratos.backend.common.dtos.Response;
import org.kratos.backend.form.dto.FormCreateRequest;
import org.kratos.backend.form.dto.FormUpdateRequest;
import org.kratos.backend.form.dto.FormResponse;
import org.kratos.backend.form.service.FormService;
import org.kratos.backend.workflow.dto.WorkflowDataUpdateRequest;
import org.kratos.backend.workflow.dto.WorkflowUpdateRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/client/private/form")
@RequiredArgsConstructor
public class FormResource {
    private final FormService formService;
    @PostMapping("/create")
    public Response<FormResponse> create(@AuthenticationPrincipal Jwt user, @Valid @RequestBody Request<FormCreateRequest> request) {
        return Response.<FormResponse>builder()
                .data(formService.create(user.getSubject(), request.data()))
                .status(ResponseStatus.ALL_OK)
                .build();
    }

    @PostMapping("/update")
    public Response<FormResponse> update(@RequestHeader("X-Subject") UUID formId,
                                             @Valid @RequestBody Request<FormUpdateRequest> workflowUpdateRequest) {
        return Response.<FormResponse>builder()
                .data(formService.update(formId, workflowUpdateRequest.data()))
                .status(ResponseStatus.ALL_OK)
                .build();
    }


    @PostMapping("/get")
    public Response<FormResponse> get(@RequestHeader("X-Subject") UUID formId,
                                          @Valid @RequestBody Request<UUID> workflowId) {
        return Response.<FormResponse>builder()
                .data(formService.get(formId))
                .status(ResponseStatus.ALL_OK)
                .build();
    }

    @PostMapping("/get/all")
    public Response<List<FormResponse>> getAll() {
        List<FormResponse> allWorkflows = formService.getAll();
        return Response.<List<FormResponse>>builder()
                .data(allWorkflows)
                .status(ResponseStatus.ALL_OK)
                .build();
    }
}
