package org.kratos.backend.workflow.mapper;

import org.kratos.backend.workflow.data.entities.DataChange;
import org.kratos.backend.workflow.data.entities.StateChange;
import org.kratos.backend.workflow.data.entities.Workflow;
import org.kratos.backend.workflow.dto.DataChangeDto;
import org.kratos.backend.workflow.dto.StateChangeDto;
import org.kratos.backend.workflow.dto.WorkflowDataUpdateRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChangeMapper {
	
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
	
	List<StateChangeDto> toStateChangeList(List<StateChange> stateChange);
	
	List<DataChangeDto> toDataChangeList(List<DataChange> dataChange);
	
}