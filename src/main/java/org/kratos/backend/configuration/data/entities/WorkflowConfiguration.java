package org.kratos.backend.configuration.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table (name = "workflow_configurations")
public class WorkflowConfiguration {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column (name = "id", nullable = false)
	private UUID id;
	
	@NotNull
	@Column (name = "config", nullable = false)
	@JdbcTypeCode (SqlTypes.JSON)
	private Map<String, Object> config;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "created_by", nullable = false)
	private String createdBy;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "updated_by", nullable = false)
	private String updatedBy;
	
	@NotNull
	@ColumnDefault ("now()")
	@Column (name = "created_at", nullable = false)
	private ZonedDateTime createdAt;
	
	@NotNull
	@ColumnDefault ("now()")
	@Column (name = "updated_at", nullable = false)
	private ZonedDateTime updatedAt;
	
	@NotNull
	@ColumnDefault ("true")
	@Column (name = "is_active", nullable = false)
	private Boolean isActive = false;
	
}