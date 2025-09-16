package org.kratos.backend.auth.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table (name = "resource_operations", schema = "kratos", uniqueConstraints = {
		@UniqueConstraint (name = "uq_resource_operations", columnNames = {"resource", "operation"})
})
public class ResourceOperation {
	
	@Id
	@Generated
	@Column (name = "id", nullable = false)
	private UUID id;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "resource", nullable = false)
	private String resource;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "operation", nullable = false)
	private String operation;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "display_name", nullable = false)
	private String displayName;
	
	@Size (max = 255)
	@Column (name = "display_description")
	private String displayDescription;
	
}