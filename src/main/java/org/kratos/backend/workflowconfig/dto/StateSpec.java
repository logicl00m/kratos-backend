package org.kratos.backend.workflowconfig.dto;

public sealed interface StateSpec permits SimpleStateSpec, SwitchStateSpec {}

