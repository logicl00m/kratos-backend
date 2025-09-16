package org.kratos.backend.auth.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table (name = "subject_roles", schema = "kratos")
@IdClass (SubjectRoleId.class)
public class SubjectRole {
	
	@Id
	@Size (max = 36)
	@NotNull
	@Column (name = "user_id", nullable = false, length = 36)
	private String userId;
	
	@Id
	@NotNull
	@Column (name = "subject", nullable = false)
	private UUID subject;
	
	@Id
	@NotNull
	@Column (name = "app_role", nullable = false)
	private UUID appRoleId;
	
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "app_role", nullable = false)
	private AppRole appRole;
	
	@NotNull
	@Column (name = "status", nullable = false)
	private Boolean status;
}