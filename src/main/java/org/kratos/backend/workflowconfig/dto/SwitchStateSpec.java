package org.kratos.backend.workflowconfig.dto;

public record SwitchStateSpec(String expression, String operation) implements StateSpec {}

