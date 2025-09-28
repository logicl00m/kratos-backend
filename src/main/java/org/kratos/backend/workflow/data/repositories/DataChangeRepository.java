package org.kratos.backend.workflow.data.repositories;

import org.kratos.backend.workflow.data.entities.DataChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataChangeRepository extends JpaRepository<DataChange, UUID> {

}