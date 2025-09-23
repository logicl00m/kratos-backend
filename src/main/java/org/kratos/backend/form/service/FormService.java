package org.kratos.backend.form.service;

import jakarta.validation.Valid;
import org.kratos.backend.form.dto.FormCreateRequest;
import org.kratos.backend.form.dto.FormResponse;
import org.kratos.backend.form.dto.FormUpdateRequest;
import org.kratos.backend.workflow.data.entities.Workflow;

import java.util.List;
import java.util.UUID;

public interface FormService {
    FormResponse create(String userId, FormCreateRequest request);

    FormResponse update(UUID formId, @Valid FormUpdateRequest request);

    FormResponse get(@Valid UUID formId);

    FormResponse getEntity(UUID formId);

    List<FormResponse> getAll();

}
