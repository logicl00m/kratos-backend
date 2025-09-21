package org.kratos.backend.workflow.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.dtos.PaginatedResponse;
import org.kratos.backend.common.dtos.Request;
import org.kratos.backend.configuration.service.WorkflowConfigurationService;
import org.kratos.backend.workflow.data.entities.Workflow;
import org.kratos.backend.workflow.data.repositories.WorkflowRepository;
import org.kratos.backend.workflow.dto.WorkflowResponse;
import org.kratos.backend.workflow.dto.WorkflowUpdateRequest;
import org.kratos.backend.workflow.mapper.WorkflowMapper;
import org.kratos.backend.workflow.service.WorkflowService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {
	
	private final WorkflowRepository workflowRepository;
	private final WorkflowConfigurationService wfConfigService;
	private final WorkflowMapper wfMapper;
	
	@Override
	@Transactional
	public WorkflowResponse create(UUID wfConfigId, String subject) {
		var wfConfig = wfConfigService.getEntity(wfConfigId);
		Workflow workflow = new Workflow();
		workflow.setData(new HashMap<>());
		workflow.setWfConfig(wfConfig);
		workflow.setState(wfConfig.parsed()
		                          .states()
		                          .initialState());
		workflowRepository.saveAndFlush(workflow);
		return wfMapper.toDto(workflow);
	}
	
	@Override
	public WorkflowResponse update(UUID wfConfigId, WorkflowUpdateRequest workflowUpdateRequest) {
		return null;
	}
	
	@Override
	public WorkflowResponse get(Request<String> workflowId) {
		return null;
	}
	
	@Override
	public PaginatedResponse<List<WorkflowResponse>> getAll(UUID wfConfigId) {
		return null;
	}
}
