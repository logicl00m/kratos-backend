package org.kratos.backend.configuration.data.repositories;

import org.kratos.backend.configuration.data.entities.WorkflowConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkflowConfigurationRepository extends JpaRepository<WorkflowConfiguration, UUID> {
	
	@Query ("""
			select wf
			from WorkflowConfiguration wf
			join SubjectRole sr on sr.subject = wf.id
			where sr.userId = :userId
			  and wf.isDeleted = false
			""")
	Page<WorkflowConfiguration> findConfigurationsByUserId(String userId, Pageable pageable);
}