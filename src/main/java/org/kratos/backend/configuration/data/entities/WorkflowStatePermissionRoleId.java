package org.kratos.backend.configuration.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class WorkflowStatePermissionRoleId implements Serializable {
	
	private static final long serialVersionUID = -5119521467279825953L;
	@NotNull
	@Column (name = "wf_role_id", nullable = false)
	private UUID wfRoleId;
	
	@NotNull
	@Column (name = "wf_state_permission_id", nullable = false)
	private UUID wfStatePermissionId;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		WorkflowStatePermissionRoleId entity = (WorkflowStatePermissionRoleId) o;
		return Objects.equals(this.wfRoleId, entity.wfRoleId) &&
				Objects.equals(this.wfStatePermissionId, entity.wfStatePermissionId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(wfRoleId, wfStatePermissionId);
	}
	
}