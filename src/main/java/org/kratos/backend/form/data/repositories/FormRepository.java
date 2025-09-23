package org.kratos.backend.form.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.kratos.backend.form.data.entities.Form;

import java.util.UUID;

public interface FormRepository extends JpaRepository<Form, UUID> {
	

}