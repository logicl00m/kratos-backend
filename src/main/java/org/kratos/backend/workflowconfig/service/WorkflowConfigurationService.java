package org.kratos.backend.workflowconfig.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.kratos.backend.workflowconfig.dto.WorkflowConfiguration;
import org.kratos.backend.workflowconfig.entity.WorkflowConfigurationEntity;
import org.kratos.backend.workflowconfig.repository.WorkflowConfigurationRepository;
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
        WorkflowConfigurationEntity entity = WorkflowConfigurationEntity.builder()
                .workflowId(configuration.id())
                .version(configuration.version())
                .configuration(objectMapper.writeValueAsString(configuration))
                .build();
        
        WorkflowConfigurationEntity savedEntity = repository.save(entity);
        return objectMapper.readValue(savedEntity.getConfiguration(), WorkflowConfiguration.class);
    }
    
    public WorkflowConfiguration update(WorkflowConfiguration configuration) throws JsonProcessingException {
        Optional<WorkflowConfigurationEntity> existingEntity = repository.findByWorkflowId(configuration.id());
        
        if (existingEntity.isPresent()) {
            WorkflowConfigurationEntity entity = existingEntity.get();
            entity.setVersion(configuration.version());
            entity.setConfiguration(objectMapper.writeValueAsString(configuration));
            
            WorkflowConfigurationEntity savedEntity = repository.save(entity);
            return objectMapper.readValue(savedEntity.getConfiguration(), WorkflowConfiguration.class);
        } else {
            return create(configuration);
        }
    }
    
    public WorkflowConfiguration get(String workflowId) throws JsonProcessingException {
        Optional<WorkflowConfigurationEntity> entity = repository.findByWorkflowId(workflowId);
        
        if (entity.isPresent()) {
            return objectMapper.readValue(entity.get().getConfiguration(), WorkflowConfiguration.class);
        }
        
        return null;
    }
    
    public List<WorkflowConfiguration> getAll() throws JsonProcessingException {
        return repository.findAll().stream()
                .map(entity -> {
                    try {
                        return objectMapper.readValue(entity.getConfiguration(), WorkflowConfiguration.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Failed to parse workflow configuration", e);
                    }
                })
                .collect(Collectors.toList());
    }
    
    public boolean delete(String workflowId) {
        Optional<WorkflowConfigurationEntity> entity = repository.findByWorkflowId(workflowId);
        
        if (entity.isPresent()) {
            repository.deleteById(entity.get().getId());
            return true;
        }
        
        return false;
    }
    
    public boolean exists(String workflowId) {
        return repository.existsByWorkflowId(workflowId);
    }
}