package org.kratos.backend.workflowconfig.repository;

import org.kratos.backend.workflowconfig.entity.WorkflowConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkflowConfigurationRepository extends JpaRepository<WorkflowConfigurationEntity, UUID> {
    Optional<WorkflowConfigurationEntity> findByWorkflowId(String workflowId);
    boolean existsByWorkflowId(String workflowId);
}