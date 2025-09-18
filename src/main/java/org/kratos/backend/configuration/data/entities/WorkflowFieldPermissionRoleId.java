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
public class WorkflowFieldPermissionRoleId implements Serializable {
	
	private static final long serialVersionUID = 8432869207980920032L;
	@NotNull
	@Column (name = "wf_role_id", nullable = false)
	private UUID wfRoleId;
	
	@NotNull
	@Column (name = "wf_field_permission_id", nullable = false)
	private UUID wfFieldPermissionId;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		WorkflowFieldPermissionRoleId entity = (WorkflowFieldPermissionRoleId) o;
		return Objects.equals(this.wfRoleId, entity.wfRoleId) &&
				Objects.equals(this.wfFieldPermissionId, entity.wfFieldPermissionId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(wfRoleId, wfFieldPermissionId);
	}
	
}