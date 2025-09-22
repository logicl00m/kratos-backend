package org.kratos.backend.workflowconfig.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Map;

public record WorkflowConfiguration(
    @NotBlank(message = "Workflow id is required")
    String id,

    @NotBlank(message = "Workflow version is required")
    String version,

    // When present, forms must be valid and non-empty
    @Valid
    @NotEmpty(message = "Forms cannot be empty when provided")
    Map<String, Form> forms,

    // When present, states must be valid
    @Valid
    States states,

    // When present, scripts must be valid
    @Valid
    Map<String, Script> scripts
) {}
