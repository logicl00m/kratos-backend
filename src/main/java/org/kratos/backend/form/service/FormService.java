package org.kratos.backend.form.service;

import jakarta.validation.Valid;
import org.kratos.backend.common.dtos.PaginatedResponse;
import org.kratos.backend.common.dtos.PaginationRequest;
import org.kratos.backend.form.data.entities.Form;
import org.kratos.backend.form.dto.FormCreateRequest;
import org.kratos.backend.form.dto.FormResponse;
import org.kratos.backend.form.dto.FormUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface FormService {
    FormResponse create(String userId, FormCreateRequest request);

    FormResponse update(String userId, @Valid FormUpdateRequest request);

    FormResponse get(@Valid UUID formId);
    
    Form getEntity(UUID formId);
    
    PaginatedResponse<List<FormResponse>> getAll(PaginationRequest pagination);
    
    void delete(String subject, @Valid UUID formId);
}
