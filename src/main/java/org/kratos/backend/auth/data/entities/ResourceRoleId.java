package org.kratos.backend.auth.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class ResourceRoleId implements Serializable {
	
	@Serial
	private static final long serialVersionUID = -392124043930344057L;
	
	@NotNull
	@Column (name = "app_role_id", nullable = false)
	private UUID appRoleId;
	
	@NotNull
	@Column (name = "resource_operation_id", nullable = false)
	private UUID resourceOperationId;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		ResourceRoleId entity = (ResourceRoleId) o;
		return Objects.equals(this.appRoleId, entity.appRoleId) &&
				Objects.equals(this.resourceOperationId, entity.resourceOperationId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(appRoleId, resourceOperationId);
	}
	
}