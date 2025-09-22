package org.kratos.backend.workflowconfig.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.configuration.data.repositories.WorkflowConfigurationRepository;
import org.kratos.backend.workflowconfig.dto.WorkflowConfiguration;
import org.kratos.backend.workflowconfig.entity.WorkflowConfigurationEntity;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkflowConfigurationService {
    
    private final WorkflowConfigurationRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public WorkflowConfiguration create(WorkflowConfiguration configuration) throws JsonProcessingException {

        return null;
    }
    
    public WorkflowConfiguration update(WorkflowConfiguration configuration) throws JsonProcessingException {
        return null;
    }
    
    public WorkflowConfiguration get(String workflowId) throws JsonProcessingException {
        return null;
    }
    
    public List<WorkflowConfiguration> getAll() throws JsonProcessingException {
        return null;
    }
    
    public boolean delete(String workflowId) {
        return true;
    }
    
    public boolean exists(String workflowId) {
        return true;
    }
}