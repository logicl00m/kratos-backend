package org.kratos.backend.workflow.service.impl;

import org.kratos.backend.common.dtos.PaginatedResponse;
import org.kratos.backend.common.dtos.Request;
import org.kratos.backend.workflow.dto.WorkflowResponse;
import org.kratos.backend.workflow.dto.WorkflowUpdateRequest;
import org.kratos.backend.workflow.service.WorkflowService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowServiceImpl implements WorkflowService {
	
	@Override
	public WorkflowResponse create(String wfConfigId) {
		return null;
	}
	
	@Override
	public WorkflowResponse update(String wfConfigId, WorkflowUpdateRequest workflowUpdateRequest) {
		return null;
	}
	
	@Override
	public WorkflowResponse get(Request<String> workflowId) {
		return null;
	}
	
	@Override
	public PaginatedResponse<List<WorkflowResponse>> getAll(String wfConfigId) {
		return null;
	}
}
