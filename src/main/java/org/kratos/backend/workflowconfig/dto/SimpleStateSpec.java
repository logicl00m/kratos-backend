package org.kratos.backend.workflowconfig.dto;

import java.util.List;
import java.util.Map;

public record SimpleStateSpec(List<FormRef> forms, Map<String, Action> actions) implements StateSpec {}

