package org.kratos.backend.configuration.mapper;

import org.kratos.backend.configuration.data.entities.WorkflowConfiguration;
import org.kratos.backend.configuration.dto.WorkflowConfigurationRequest;
import org.kratos.backend.configuration.dto.WorkflowConfigurationResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkflowConfigurationMapper {
	
	@Mapping (target = "id", ignore = true)
	@Mapping (target = "createdBy", expression = "java(userId)")
	@Mapping (target = "updatedBy", expression = "java(userId)")
	WorkflowConfiguration toEntity(WorkflowConfigurationRequest workflowConfigurationRequest, @Context String userId);
	
	WorkflowConfigurationResponse toDto(WorkflowConfiguration workflowConfiguration);
	
	List<WorkflowConfigurationResponse> toResponseList(List<WorkflowConfiguration> configurations);
}