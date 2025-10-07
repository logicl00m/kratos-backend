package org.kratos.backend.workflow.data.repositories;

import jakarta.validation.constraints.NotNull;
import org.kratos.backend.workflow.data.entities.StateChange;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StateChangeRepository extends JpaRepository<StateChange, UUID> {
	
	List<StateChange> getStateChangeByWfId(@NotNull UUID wfId, Sort sort);
}