package org.kratos.backend.workflow.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.kratos.backend.configuration.data.entities.WorkflowConfiguration;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table (name = "workflows", schema = "kratos")
public class Workflow {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column (name = "id", nullable = false)
	private UUID id;
	
	@NotNull
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "wf_config_id", nullable = false)
	private WorkflowConfiguration wfConfig;
	
	@NotNull
	@Column (name = "data", nullable = false)
	@JdbcTypeCode (SqlTypes.JSON)
	private Map<String, Object> data;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "state", nullable = false)
	private String state;
	
	@Size (max = 100)
	@NotNull
	@Column (name = "created_by", nullable = false, length = 100)
	private String createdBy;
	
	@Size (max = 100)
	@NotNull
	@Column (name = "updated_by", nullable = false, length = 100)
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