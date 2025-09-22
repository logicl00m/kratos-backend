package org.kratos.backend.workflow.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.kratos.backend.configuration.data.entities.WorkflowConfiguration;

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
	@ManyToOne (fetch = FetchType.EAGER, optional = false)
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
	
	@NotNull
	@Column (name = "is_archived", nullable = false)
	private Boolean isArchived = false;
	
}