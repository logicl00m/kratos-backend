 package org.kratos.backend.workflowconfig.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record State(
    @NotBlank(message = "State name is required")
    String name,

    @NotBlank(message = "State kind is required")
    @Pattern(regexp = "simple|switch|allOf|anyOf|doWhile",
             message = "State kind must be one of: simple, switch, allOf, anyOf, doWhile")
    String kind,

    @NotNull(message = "State spec is required")
    @Valid
    StateSpec spec
) {}
