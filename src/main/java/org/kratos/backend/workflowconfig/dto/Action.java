package org.kratos.backend.workflowconfig.dto;

import jakarta.validation.constraints.NotBlank;

public record Action(
    @NotBlank(message = "Action name is required")
    String name,

    @NotBlank(message = "Action nextState is required")
    String nextState,

    // Optional - validation and operation are not required in schema
    String validation,
    String operation
) {}
