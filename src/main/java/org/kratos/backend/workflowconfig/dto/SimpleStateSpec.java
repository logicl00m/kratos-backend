package org.kratos.backend.workflowconfig.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public record SimpleStateSpec(
    @NotNull(message = "Forms cannot be null")
    @NotEmpty(message = "Simple state must have at least one form")
    @Valid
    List<FormRef> forms,

    @NotNull(message = "Actions cannot be null")
    @NotEmpty(message = "Simple state must have at least one action")
    @Valid
    Map<String, Action> actions
) implements StateSpec {}
