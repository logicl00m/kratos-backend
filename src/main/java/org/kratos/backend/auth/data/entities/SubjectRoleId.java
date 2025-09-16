package org.kratos.backend.auth.data.entities;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class SubjectRoleId implements Serializable {
	
	@Serial
	private static final long serialVersionUID = -2755271056971672756L;
	
	@Size (max = 36)
	@NotNull
	@Column (name = "user_id", nullable = false, length = 36)
	private String userId;
	
	@NotNull
	@Column (name = "subject", nullable = false)
	private UUID subject;
	
	@NotNull
	@Column (name = "app_role", nullable = false)
	private UUID appRoleId;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		SubjectRoleId entity = (SubjectRoleId) o;
		return Objects.equals(this.subject, entity.subject) &&
				Objects.equals(this.appRoleId, entity.appRoleId) &&
				Objects.equals(this.userId, entity.userId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(subject, appRoleId, userId);
	}
	
}