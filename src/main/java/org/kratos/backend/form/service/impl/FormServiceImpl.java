package org.kratos.backend.form.service.impl;


import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.exceptions.BaseException;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.form.data.entities.Form;
import org.kratos.backend.form.data.repositories.FormRepository;
import org.kratos.backend.form.dto.FormResponse;
import org.kratos.backend.form.dto.FormUpdateRequest;
import org.kratos.backend.form.dto.FormCreateRequest;
import org.kratos.backend.form.service.FormService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {
    private final FormRepository formRepository;

    public FormResponse create(String userId,FormCreateRequest request) {
        try {
            Map<String, Object> config = request.formJson();
            Form form = new Form();
            form.setConfig(config);
            form.setCreatedBy(userId);
            form.setUpdatedBy(userId);
            Form saved = formRepository.saveAndFlush(form);
            String formName = extractFormName(saved.getConfig());
            return new FormResponse(formName, saved.getId(), saved.getConfig());
        } catch (Exception e) {
            throw BaseException.builder().responseStatus(ResponseStatus.INTERNAL_ERROR).build();
        }
    }

    @Override
    public FormResponse update(UUID formId, FormUpdateRequest request) {
        return null;
    }

    @Override
    public FormResponse get(UUID formId) {
        return null;
    }

    @Override
    public FormResponse getEntity(UUID formId) {
        return null;
    }

    @Override
    public List<FormResponse> getAll() {
        try {
            return formRepository.findAll()
                    .stream()
                    .map(f -> new FormResponse(extractFormName(f.getConfig()), f.getId(), f.getConfig()))
                    .toList();
        } catch (Exception e) {
            throw BaseException.builder().responseStatus(ResponseStatus.INTERNAL_ERROR).build();
        }
    }

    private String extractFormName(Map<String, Object> config) {
        if (config == null || config.isEmpty()) {
            return "";
        }
        return config.keySet().iterator().next();
    }

}
