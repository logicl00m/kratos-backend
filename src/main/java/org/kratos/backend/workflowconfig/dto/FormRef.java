
package org.kratos.backend.workflowconfig.dto;

import jakarta.validation.constraints.NotBlank;

public record FormRef(
    @NotBlank(message = "Form reference id is required")
    String id
) {}
