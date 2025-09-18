package org.kratos.backend.configuration.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table (name = "workflow_state_permissions")
public class WorkflowStatePermission {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column (name = "id", nullable = false)
	private UUID id;
	
	@NotNull
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "wf_config_id", nullable = false)
	private WorkflowConfiguration wfConfig;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "state", nullable = false)
	private String state;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "action", nullable = false)
	private String action;
	
	@NotNull
	@ColumnDefault ("now()")
	@Column (name = "created_at", nullable = false)
	private ZonedDateTime createdAt;
	
	@NotNull
	@ColumnDefault ("now()")
	@Column (name = "updated_at", nullable = false)
	private ZonedDateTime updatedAt;
	
}