package org.kratos.backend.workflowconfig.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

public record Field(
    @NotBlank(message = "Field name is required")
    String name,

    @NotNull(message = "Field data cannot be null")
    String data,

    @NotNull(message = "Field actions cannot be null")
    @NotEmpty(message = "Field must have at least one action")
    List<String> actions,

    @NotBlank(message = "Field kind is required")
    @Pattern(regexp = "text|number", message = "Field kind must be 'text' or 'number'")
    String kind,

    @NotNull(message = "Field spec is required")
    @Valid
    FieldSpec spec
) {}
