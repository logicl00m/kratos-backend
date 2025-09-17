package org.kratos.backend.core.domain.request;

import jakarta.validation.constraints.NotBlank;

public record CreateWorkflowConfigRequest(
        @NotBlank(message = "configJson must not be blank") String configJson,
        @NotBlank(message = "formId must not be blank") String formId) {
}