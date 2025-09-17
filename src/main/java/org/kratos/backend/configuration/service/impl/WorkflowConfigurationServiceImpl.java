package org.kratos.backend.configuration.service.impl;

import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.dtos.PaginatedResponse;
import org.kratos.backend.configuration.dto.WorkflowConfigurationRequest;
import org.kratos.backend.configuration.dto.WorkflowConfigurationResponse;
import org.kratos.backend.configuration.service.WorkflowConfigurationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkflowConfigurationServiceImpl implements WorkflowConfigurationService {
	
	@Override
	public WorkflowConfigurationResponse create(String userId, WorkflowConfigurationRequest wfConfig) {
		//do validation on config
		
		//add metadata, created by etc,
		
		//save to db use transactional
		
		//return the saved configId, and config
		return null;
	}
	
	@Override
	public WorkflowConfigurationResponse update(UUID wfConfigId, WorkflowConfigurationRequest wfConfig) {
		//check if config exists
		
		//validate the update request
		
		//if valid then update the config
		
		//save to db
		
		//return updated config
		return null;
	}
	
	@Override
	public WorkflowConfigurationResponse get(UUID wfConfigId) {
		//check if config exists
		
		//check if user has permission to access the config
		
		//return the config
		return null;
	}
	
	@Override
	public PaginatedResponse<List<WorkflowConfigurationResponse>> getAll(String userId) {
		//fetch permission for user
		
		//now check with workflow configs and return all with proper permission
		
		//return response in a list
		return null;
	}
	
	@Override
	public void delete(UUID workflowConfigId) {
		//
	}
}
