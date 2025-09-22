package org.kratos.backend.workflowconfig.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record Form(
    @NotNull(message = "Fields cannot be null")
    @NotEmpty(message = "Form must have at least one field")
    @Valid
    Map<String, Field> fields
) {}
