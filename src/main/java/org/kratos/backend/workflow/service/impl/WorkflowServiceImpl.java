package org.kratos.backend.workflow.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.exceptions.BaseException;
import org.kratos.backend.configuration.service.WorkflowConfigurationService;
import org.kratos.backend.workflow.data.entities.Workflow;
import org.kratos.backend.workflow.data.repositories.WorkflowRepository;
import org.kratos.backend.workflow.dto.WorkflowDataUpdateRequest;
import org.kratos.backend.workflow.dto.WorkflowResponse;
import org.kratos.backend.workflow.dto.WorkflowUpdateRequest;
import org.kratos.backend.workflow.mapper.WorkflowMapper;
import org.kratos.backend.workflow.service.WorkflowService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.kratos.backend.common.constants.ResponseStatus.WF_NOT_FOUND;

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
    public WorkflowResponse update(WorkflowUpdateRequest workflowUpdateRequest) {
        return null;
    }

    @Override
    @Transactional
    public WorkflowResponse updateData(WorkflowDataUpdateRequest request) {
        var workflow = getEntity(request.id());
        workflow.setData(request.data());
        workflowRepository.saveAndFlush(workflow);
        return wfMapper.toDto(workflow);
    }

    @Override
    public WorkflowResponse get(UUID workflowId) {
        return null;
    }

    @Override
    public Workflow getEntity(UUID workflowId) {
        return workflowRepository.findById(workflowId)
                .orElseThrow(() -> BaseException.builder()
                        .responseStatus(WF_NOT_FOUND)
                        .build());
    }


    @Override
    public List<WorkflowResponse> getAll() {
        var workflows = workflowRepository.findAll();
        return workflows.stream()
                .map(wfMapper::toDto)
                .toList();

    }
}
