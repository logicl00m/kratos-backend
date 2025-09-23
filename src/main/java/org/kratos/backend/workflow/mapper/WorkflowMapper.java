package org.kratos.backend.workflow.mapper;

import org.kratos.backend.workflow.data.entities.Workflow;
import org.kratos.backend.workflow.dto.WorkflowResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkflowMapper {
	
	WorkflowResponse toDto(Workflow workflow);
	
	List<WorkflowResponse> toResponseList(List<Workflow> content);
}