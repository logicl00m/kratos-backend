package org.kratos.backend.workflow.data.repositories;

import org.kratos.backend.workflow.data.entities.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkflowRepository extends JpaRepository<Workflow, UUID> {

}