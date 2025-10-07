package org.kratos.backend.form.data.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.kratos.backend.form.data.entities.Form;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FormRepository extends JpaRepository<Form, UUID> {
	
	@Query ("select f from Form f where f.id = :id and f.isDeleted = false")
	@NotNull
	Optional<Form> findById(@Param ("id") @NotNull UUID id);
	
	@Query ("select f from Form f where f.isDeleted = false")
	@NotNull
	Page<Form> findAll(@NotNull Pageable pageable);
}