package org.kratos.backend.workflowconfig.dto;

import java.util.List;

public record WorkflowValidationErrorResponse(
    boolean valid,
    String message,
    List<WorkflowValidationError> errors
) {
    public record WorkflowValidationError(
        String field,
        String code,
        String message,
        String suggestion,
        Object invalidValue
    ) {}
}
