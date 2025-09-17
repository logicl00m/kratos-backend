package org.kratos.backend.core.domain.response;

import jakarta.validation.constraints.NotBlank;

public record CreateWorkflowConfigResponse(
        @NotBlank(message = "message must not be blank") String message,
        @NotBlank(message = "configId must not be blank") String configId,
        @NotBlank(message = "config json must not be blank") String configJson) {
}
