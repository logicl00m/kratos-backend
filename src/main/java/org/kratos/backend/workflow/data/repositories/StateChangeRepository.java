package org.kratos.backend.workflow.data.repositories;

import org.kratos.backend.workflow.data.entities.StateChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StateChangeRepository extends JpaRepository<StateChange, UUID> {

}