package org.kratos.backend.workflowconfig.dto;

import java.util.Map;

public record WorkflowConfiguration(
    String id,
    String version,
    Map<String, Form> forms,
    States states,
    Map<String, Script> scripts
) {}

