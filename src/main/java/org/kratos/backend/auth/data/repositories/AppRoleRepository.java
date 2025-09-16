package org.kratos.backend.auth.data.repositories;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.kratos.backend.auth.data.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppRoleRepository extends JpaRepository<AppRole, UUID> {
	
	AppRole getAppRoleByAppRole(@Size (max = 255) @NotNull String appRole);
}