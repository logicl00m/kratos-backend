package org.kratos.backend.forms.presenter.rest;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record FormFieldDTO(
    @NotBlank(message = "Field ID is required")
    String id,
    
    @NotBlank(message = "Field name is required")
    String name,
    
    @NotBlank(message = "Field type is required")
    String type,        // 'text' | 'number' | 'textarea' | 'file' | 'select' | 'radio' | 'checkbox' | 'date' | 'section' | 'divider'
    
    String status,      // 'default' | 'readonly' | 'disabled'
    String data,        // e.g. "{{ data.borrower.legalName }}"
    List<String> fieldActions,
    FormValidationDTO validation,
    List<FormOptionDTO> options,
    String helpText
) {}