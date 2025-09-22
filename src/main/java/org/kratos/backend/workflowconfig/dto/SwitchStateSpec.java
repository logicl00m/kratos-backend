package org.kratos.backend.workflowconfig.dto;

import jakarta.validation.constraints.NotBlank;

public record SwitchStateSpec(
    @NotBlank(message = "Switch state expression is required")
    String expression,

    // Optional - operation is not required in schema
    String operation
) implements StateSpec {}
