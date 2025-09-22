package org.kratos.backend.forms.presenter.rest;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FormUpdateRequest(
    @NotBlank(message = "Form name is required")
    String name, 
    
    @NotNull(message = "Form JSON structure is required")
    Map<String, FormSection> json, 
    
    Boolean bumpVersion
) {}