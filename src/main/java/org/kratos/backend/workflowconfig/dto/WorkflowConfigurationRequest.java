package org.kratos.backend.workflowconfig.dto;

import org.kratos.backend.common.validators.ValidJsonSchema;
import jakarta.validation.constraints.NotNull;

public record WorkflowConfigurationRequest(
    @NotNull(message = "Configuration cannot be null")
    @ValidJsonSchema(schema = "/schemas/v1.configuration.schema.json")
    WorkflowConfiguration configuration
) {}
