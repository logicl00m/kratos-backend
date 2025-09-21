package org.kratos.backend.configuration.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.auth.service.ResourceOperationService;
import org.kratos.backend.common.dtos.PaginatedResponse;
import org.kratos.backend.common.dtos.PaginationRequest;
import org.kratos.backend.common.dtos.PaginationResponse;
import org.kratos.backend.common.exceptions.BaseException;
import org.kratos.backend.configuration.data.entities.WorkflowConfiguration;
import org.kratos.backend.configuration.data.repositories.WorkflowConfigurationRepository;
import org.kratos.backend.configuration.dto.WorkflowConfigurationRequest;
import org.kratos.backend.configuration.dto.WorkflowConfigurationResponse;
import org.kratos.backend.configuration.mapper.WorkflowConfigurationMapper;
import org.kratos.backend.configuration.service.WorkflowConfigurationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.kratos.backend.auth.constants.PredefinedRoles.WORKFLOW_ADMIN;
import static org.kratos.backend.common.constants.ResponseStatus.FAILED_TO_LOAD_WF_SCHEMA;
import static org.kratos.backend.common.constants.ResponseStatus.WF_CONFIG_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class WorkflowConfigurationServiceImpl implements WorkflowConfigurationService {
	
	private final WorkflowConfigurationRepository wfConfigRepository;
	private final WorkflowConfigurationMapper wfConfigMapper;
	private final ResourceOperationService resourceOperationService;
	
	@Override
	@Transactional
	public WorkflowConfigurationResponse create(String userId, @Valid WorkflowConfigurationRequest request) {
		// fixme(high): make sure that config.(id, version) is unique
		WorkflowConfiguration wfConfig = wfConfigMapper.toEntity(request, userId);
		wfConfigRepository.saveAndFlush(wfConfig);
		
		UUID facilityAdmin = resourceOperationService.getPredefinedRoleByName(WORKFLOW_ADMIN)
		                                             .getId();
		resourceOperationService.assignRoleBySubject(userId,
		                                             wfConfig.getId(),
		                                             facilityAdmin, true);
		
		return wfConfigMapper.toDto(wfConfig);
	}
	
	@Override
	public WorkflowConfigurationResponse update(UUID wfConfigId, @Valid WorkflowConfigurationRequest wfConfig,
	                                            String userId) {
		wfConfigRepository.findById(wfConfigId)
		                  .orElseThrow(() -> BaseException.builder()
		                                                  .responseStatus(WF_CONFIG_NOT_FOUND)
		                                                  .build());
		
		var entity = wfConfigMapper.toEntity(wfConfig, userId);
		entity.setId(wfConfigId);
		wfConfigRepository.saveAndFlush(entity);
		return wfConfigMapper.toDto(entity);
	}
	
	@Override
	public WorkflowConfigurationResponse get(UUID wfConfigId) {
		WorkflowConfiguration wfConfig = wfConfigRepository.findById(wfConfigId)
		                                                   .orElseThrow(() -> BaseException.builder()
		                                                                                   .responseStatus(
				                                                                                   WF_CONFIG_NOT_FOUND)
		                                                                                   .build());
		return wfConfigMapper.toDto(wfConfig);
	}
	
	@Override
	public PaginatedResponse<List<WorkflowConfigurationResponse>> getAll(String userId, PaginationRequest page) {
		Pageable pageable = PageRequest.of(page.number(), page.size());
		Page<WorkflowConfiguration> configurations = wfConfigRepository.findConfigurationsByUserId(userId, pageable);
		return new PaginatedResponse<>(wfConfigMapper.toResponseList(configurations.getContent()),
		                               new PaginationResponse(configurations));
	}
	
	@Override
	public void delete(UUID wfConfigId) {
		WorkflowConfiguration wfConfig = wfConfigRepository.findById(wfConfigId)
		                                                   .orElseThrow(() -> BaseException.builder()
		                                                                                   .responseStatus(
				                                                                                   WF_CONFIG_NOT_FOUND)
		                                                                                   .build());
		wfConfig.setIsDeleted(true);
		resourceOperationService.removeRolesFromSubject(wfConfigId);
		wfConfigRepository.save(wfConfig);
	}
	
	@Override
	public Map<String, Object> getSchema(String version) {
		try (InputStream schemaStream = getClass().getResourceAsStream(
				String.format("/schemas/%s.configuration.schema.json", version))) {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(schemaStream, new TypeReference<Map<String, Object>>() {
			});
		} catch (Exception e) {
			throw BaseException.builder()
			                   .responseStatus(FAILED_TO_LOAD_WF_SCHEMA)
			                   .build();
		}
	}
}
