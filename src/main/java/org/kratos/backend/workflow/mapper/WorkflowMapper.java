package org.kratos.backend.workflow.mapper;

import org.kratos.backend.workflow.data.entities.DataChange;
import org.kratos.backend.workflow.data.entities.StateChange;
import org.kratos.backend.workflow.data.entities.Workflow;
import org.kratos.backend.workflow.dto.WorkflowDataUpdateRequest;
import org.kratos.backend.workflow.dto.WorkflowResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkflowMapper {
	
	WorkflowResponse toDto(Workflow workflow);
	
	List<WorkflowResponse> toResponseList(List<Workflow> content);
	
	@Mapping (target = "id", ignore = true)
	@Mapping (target = "wfId", expression = "java(workflow.getId())")
	@Mapping (target = "currentState", expression = "java(workflow.getState())")
	@Mapping (target = "diff", source = "diff")
	@Mapping (target = "createdBy", expression = "java(userId)")
	@Mapping (target = "createdAt", ignore = true)
	DataChange dataChange(WorkflowDataUpdateRequest request, @Context Workflow workflow, @Context String userId);
	
	@Mapping (target = "id", ignore = true)
	@Mapping (target = "wfId", expression = "java(workflow.getId())")
	@Mapping (target = "fromState", expression = "java(workflow.getState())")
	@Mapping (target = "toState", ignore = true)
	@Mapping (target = "action", source = "action")
	@Mapping (target = "createdBy", expression = "java(userId)")
	@Mapping (target = "createdAt", ignore = true)
	StateChange stateChange(String action,
	                        @Context Workflow workflow,
	                        @Context String userId);
}