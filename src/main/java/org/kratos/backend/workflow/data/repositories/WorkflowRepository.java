package org.kratos.backend.workflow.data.repositories;

import org.kratos.backend.workflow.data.entities.Workflow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface WorkflowRepository extends JpaRepository<Workflow, UUID> {
	
	@Query ("select w from Workflow w where w.wfConfig.id = :wfConfigId")
	Page<Workflow> findAllByWfConfig_Id(@Param("wfConfigId") UUID wfConfigId, Pageable pageable);
}