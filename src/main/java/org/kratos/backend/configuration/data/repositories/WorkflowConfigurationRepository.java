package org.kratos.backend.configuration.data.repositories;

import org.kratos.backend.configuration.data.entities.WorkflowConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkflowConfigurationRepository extends JpaRepository<WorkflowConfiguration, UUID> {

}