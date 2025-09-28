package org.kratos.backend.workflow.mapper;

import lombok.Setter;
import org.kratos.backend.workflow.data.entities.DataChange;
import org.kratos.backend.workflow.data.entities.StateChange;
import org.kratos.backend.workflow.data.entities.Workflow;
import org.kratos.backend.workflow.data.repositories.DataChangeRepository;
import org.kratos.backend.workflow.data.repositories.StateChangeRepository;
import org.kratos.backend.workflow.dto.WorkflowResponse;
import org.kratos.backend.workflow.dto.WorkflowResponse.HistoryDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

@Setter (onMethod = @__ (@Autowired))
@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WorkflowMapper {
	
	private DataChangeRepository dataChangeRepository;
	private StateChangeRepository stateChangeRepository;
	private ChangeMapper changeMapper;
	
	@Mapping (target = "history", expression = "java(getHistory(workflow.getId()))")
	public abstract WorkflowResponse toDto(Workflow workflow);
	
	public abstract List<WorkflowResponse> toResponseList(List<Workflow> content);
	
	protected HistoryDto getHistory(UUID workflowId) {
		List<DataChange> dataChangeByWfId = dataChangeRepository.getDataChangeByWfId(workflowId, Sort.by("createdAt"));
		List<StateChange> stateChangeByWfId = stateChangeRepository.getStateChangeByWfId(workflowId,
		                                                                                 Sort.by("createdAt"));
		return new HistoryDto(changeMapper.toDataChangeList(dataChangeByWfId),
		                      changeMapper.toStateChangeList(stateChangeByWfId));
	}
}