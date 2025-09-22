package org.kratos.backend.workflowconfig.dto;

public record NumberFieldSpec(String placeholder, Double min, Double max) implements FieldSpec {}

