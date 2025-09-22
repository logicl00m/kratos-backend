package org.kratos.backend.workflowconfig.dto;

import jakarta.validation.constraints.NotNull;

public record NumberFieldSpec(
    @NotNull(message = "Number field placeholder is required")
    String placeholder,

    // Optional - min and max are not required in schema
    Double min,
    Double max
) implements FieldSpec {}
