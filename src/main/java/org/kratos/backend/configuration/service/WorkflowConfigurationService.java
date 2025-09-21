package org.kratos.backend.configuration.service;

import jakarta.validation.Valid;
import org.kratos.backend.common.dtos.PaginatedResponse;
import org.kratos.backend.common.dtos.PaginationRequest;
import org.kratos.backend.configuration.dto.WorkflowConfigurationRequest;
import org.kratos.backend.configuration.dto.WorkflowConfigurationResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface WorkflowConfigurationService {
	
	WorkflowConfigurationResponse create(String userId, @Valid WorkflowConfigurationRequest wfConfig);
	
	WorkflowConfigurationResponse update(UUID wfConfigId, @Valid WorkflowConfigurationRequest wfConfig, String subject);
	
	WorkflowConfigurationResponse get(UUID wfConfigId);
	
	PaginatedResponse<List<WorkflowConfigurationResponse>> getAll(String userId, PaginationRequest page);
	
	void delete(UUID wfConfigId);
	
	Map<String, Object> getSchema(String version);
}
