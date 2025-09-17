package org.kratos.backend.core.domain.response;

import java.util.List;

public record GetAllWorkflowConfigsResponse(List<GetWorkflowConfigResponse> configs) {
}
