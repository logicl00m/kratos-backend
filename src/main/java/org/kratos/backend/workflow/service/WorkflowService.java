package org.kratos.backend.workflow.service;

import org.kratos.backend.common.dtos.PaginatedResponse;
import org.kratos.backend.common.dtos.PaginationRequest;
import org.kratos.backend.workflow.data.entities.Workflow;
import org.kratos.backend.workflow.dto.WorkflowDataUpdateRequest;
import org.kratos.backend.workflow.dto.WorkflowResponse;
import org.kratos.backend.workflow.dto.WorkflowUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface WorkflowService {
	
	WorkflowResponse create(UUID wfConfigId, String subject);
	
	WorkflowResponse updateState(WorkflowUpdateRequest workflowUpdateRequest, String userId);
	
	WorkflowResponse updateData(WorkflowDataUpdateRequest data, String userId);
	
	WorkflowResponse get(UUID workflowId);
	
	Workflow getEntity(UUID workflowId);
	
	PaginatedResponse<List<WorkflowResponse>> getAll(UUID wfConfigId, PaginationRequest pagination);
}
