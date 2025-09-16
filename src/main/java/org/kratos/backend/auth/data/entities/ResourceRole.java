package org.kratos.backend.auth.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "resource_roles", schema = "kratos")
public class ResourceRole {
	
	@EmbeddedId
	private ResourceRoleId id;
	
	@MapsId ("appRoleId")
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "app_role_id", nullable = false)
	private AppRole appRole;
	
	@MapsId ("resourceOperationId")
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "resource_operation_id", nullable = false)
	private ResourceOperation resourceOperation;
	
}