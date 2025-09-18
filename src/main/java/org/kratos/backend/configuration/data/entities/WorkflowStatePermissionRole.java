package org.kratos.backend.configuration.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.kratos.backend.auth.data.entities.AppRole;

@Getter
@Setter
@Entity
@Table (name = "workflow_state_permission_roles")
public class WorkflowStatePermissionRole {
	
	@EmbeddedId
	private WorkflowStatePermissionRoleId id;
	
	@MapsId ("wfRoleId")
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "wf_role_id", nullable = false)
	private AppRole wfRole;
	
	@MapsId ("wfStatePermissionId")
	@ManyToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "wf_state_permission_id", nullable = false)
	private WorkflowStatePermission wfStatePermission;
	
}