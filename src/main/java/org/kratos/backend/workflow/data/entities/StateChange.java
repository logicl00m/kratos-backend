package org.kratos.backend.workflow.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table (name = "state_changes")
public class StateChange {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column (name = "id", nullable = false)
	private UUID id;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "from_state", nullable = false)
	private String fromState;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "to_state", nullable = false)
	private String toState;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "action", nullable = false)
	private String action;
	
	@NotNull
	@Column (name = "wf_id", nullable = false, updatable = false)
	private UUID wfId;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "created_by", nullable = false)
	private String createdBy;
	
	@Generated (event = EventType.INSERT)
	@Column (name = "created_at", nullable = false, updatable = false, insertable = false)
	private ZonedDateTime createdAt;
	
}