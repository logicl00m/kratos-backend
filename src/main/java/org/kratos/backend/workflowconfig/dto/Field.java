package org.kratos.backend.workflowconfig.dto;

import java.util.List;

public record Field(String name, String data, List<String> actions, String kind, FieldSpec spec) {}

