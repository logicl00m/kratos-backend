package org.kratos.backend.workflowconfig.dto;

import java.util.Map;

public record Script(
    // Schema allows empty script objects, so props can be empty but not null
    Map<String, Object> props
) {}
