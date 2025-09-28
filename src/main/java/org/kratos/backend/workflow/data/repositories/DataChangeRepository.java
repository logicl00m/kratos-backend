package org.kratos.backend.workflow.data.repositories;

import jakarta.validation.constraints.NotNull;
import org.kratos.backend.workflow.data.entities.DataChange;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DataChangeRepository extends JpaRepository<DataChange, UUID> {
	
	List<DataChange> getDataChangeByWfId(@NotNull UUID wfId, Sort sort);
}