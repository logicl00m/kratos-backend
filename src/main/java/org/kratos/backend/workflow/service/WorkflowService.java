package org.kratos.backend.workflow.service;

import jakarta.validation.Valid;
import org.kratos.backend.common.dtos.PaginatedResponse;
import org.kratos.backend.workflow.data.entities.Workflow;
import org.kratos.backend.workflow.dto.WorkflowDataUpdateRequest;
import org.kratos.backend.workflow.dto.WorkflowResponse;
import org.kratos.backend.workflow.dto.WorkflowUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface WorkflowService {
	
	WorkflowResponse create(UUID wfConfigId, String subject);
	
	WorkflowResponse updateState(@Valid WorkflowUpdateRequest workflowUpdateRequest);
	
	WorkflowResponse updateData(@Valid WorkflowDataUpdateRequest data);
	
	WorkflowResponse get(@Valid UUID workflowId);
	
	Workflow getEntity(UUID workflowId);
	
	PaginatedResponse<List<WorkflowResponse>> getAll(UUID wfConfigId);
	
}
