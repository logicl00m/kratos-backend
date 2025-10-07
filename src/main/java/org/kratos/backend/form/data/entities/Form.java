package org.kratos.backend.form.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.generator.EventType;
import org.hibernate.type.SqlTypes;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table (name = "forms")
public class Form {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private UUID id;
	
	@Size(max = 255)
	@NotNull
	@Column (name = "name", nullable = false)
	private String name;

	@NotNull
	@Column (name = "config", nullable = false)
	@JdbcTypeCode(SqlTypes.JSON)
	private Map<String, Object> config;

	@Size(max = 255)
	@NotNull
	@Column (name = "created_by", nullable = false)
	private String createdBy;

	@Size (max = 255)
	@NotNull
	@Column (name = "updated_by", nullable = false)
	private String updatedBy;

	@Generated(event = EventType.INSERT)
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

}
