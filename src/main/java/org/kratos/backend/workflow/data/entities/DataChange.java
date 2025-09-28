package org.kratos.backend.workflow.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.generator.EventType;
import org.hibernate.type.SqlTypes;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table (name = "data_changes")
public class DataChange {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column (name = "id", nullable = false)
	private UUID id;
	
	@NotNull
	@Column (name = "diff", nullable = false, updatable = false)
	@JdbcTypeCode (SqlTypes.JSON)
	private Map<String, Object> diff;
	
	@Size (max = 255)
	@Column (name = "current_state", nullable = false, updatable = false)
	private String currentState;
	
	@NotNull
	@Column (name = "wf_id", nullable = false, updatable = false)
	private UUID wfId;
	
	@Size (max = 255)
	@NotNull
	@Column (name = "created_by", nullable = false, updatable = false)
	private String createdBy;
	
	@Generated (event = EventType.INSERT)
	@Column (name = "created_at", nullable = false, updatable = false, insertable = false)
	private ZonedDateTime createdAt;
	
}