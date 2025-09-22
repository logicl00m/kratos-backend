package org.kratos.backend.workflowconfig.dto;

public record Action(String name, String nextState, String validation, String operation) {}

