package org.kratos.backend.configuration.data.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.generator.EventType;
import org.hibernate.type.SqlTypes;
import org.kratos.backend.configuration.dto.ParsedWorkflowConfiguration;

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
	
	@Generated (event = EventType.INSERT)
	@Column (name = "created_at", nullable = false, updatable = false, insertable = false)
	private ZonedDateTime createdAt;
	
	@UpdateTimestamp
	@Column (name = "updated_at", nullable = false)
	private ZonedDateTime updatedAt;
	
	@NotNull
	@Column (name = "is_active", nullable = false)
	private Boolean isActive = true;
	
	@NotNull
	@Column (name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;
	
	@Transient
	public ParsedWorkflowConfiguration parsed() {
		return new ObjectMapper().convertValue(config, ParsedWorkflowConfiguration.class);
	}
	
}