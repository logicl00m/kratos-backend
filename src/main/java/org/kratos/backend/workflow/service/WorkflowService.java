package org.kratos.backend.workflow.service;

import jakarta.validation.Valid;
import org.kratos.backend.common.dtos.PaginatedResponse;
import org.kratos.backend.common.dtos.Request;
import org.kratos.backend.workflow.dto.WorkflowResponse;
import org.kratos.backend.workflow.dto.WorkflowUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface WorkflowService {
	
	WorkflowResponse create(UUID wfConfigId, String subject);
	
	WorkflowResponse update(UUID wfConfigId, @Valid WorkflowUpdateRequest workflowUpdateRequest);
	
	WorkflowResponse get(@Valid Request<String> workflowId);
	
	PaginatedResponse<List<WorkflowResponse>> getAll(UUID wfConfigId);
}
