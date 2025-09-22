package org.kratos.backend.workflowconfig.dto;

import jakarta.validation.constraints.NotNull;

public record TextFieldSpec(
    @NotNull(message = "Text field placeholder is required")
    String placeholder
) implements FieldSpec {}
