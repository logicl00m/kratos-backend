package org.kratos.backend.auth.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table (name = "app_roles", schema = "kratos")
public class AppRole {
	
	@Id
	@Generated
	@Column (name = "id", nullable = false)
	private UUID id;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "app_role", nullable = false)
	private String appRole;
	
	@NotNull
	@Column(name = "is_predefined", nullable = false)
	private Boolean isPredefined = false;
}