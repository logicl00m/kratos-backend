package org.kratos.backend.auth.data.repositories;

import jakarta.validation.constraints.NotNull;
import org.kratos.backend.auth.data.entities.AppRole;
import org.kratos.backend.auth.data.entities.ResourceOperation;
import org.kratos.backend.auth.data.entities.SubjectRole;
import org.kratos.backend.auth.data.entities.SubjectRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface SubjectRoleRepository extends JpaRepository<SubjectRole, SubjectRoleId>, JpaSpecificationExecutor<SubjectRole> {
	
	
	@Query ("""
			select distinct ro
			from SubjectRole sr
			join sr.appRole ar
			join ResourceRole rr on rr.appRole = ar
			join rr.resourceOperation ro
			where sr.userId = :userId and sr.subject = :subject and sr.status = true
			""")
	Set<ResourceOperation> findResourceOperationsByUserIdAndSubject(String userId, UUID subject);
	
	@Query ("select sr.appRole from SubjectRole sr where sr.userId = :userId and sr.subject = :subject and sr.status = true")
	Set<AppRole> findRolesByUserIdAndSubject(String userId, UUID subject);
	
	@Query ("select sr.appRole from SubjectRole sr where sr.userId = :userId and sr.subject = :subject")
	Set<AppRole> findAllRolesByUserIdAndSubject(String userId, UUID subject);
	
	List<SubjectRole> findByUserIdAndStatusTrue(String userId);
	
	List<SubjectRole> findByUserId(String userId);
	
	@Modifying
	@Query (value = """
			insert into subject_roles (user_id, subject, app_role, status)
			values (:userId, :subjectId, :roleId, :status)
			on conflict (user_id, subject, app_role)
			do update set status = excluded.status
			""",
			nativeQuery = true)
	void assignRoleBySubjectToUser(String userId, UUID subjectId, UUID roleId, Boolean status);
	
	void deleteAllByUserIdAndSubject(String userId, UUID subjectId);
	
	void deleteAllBySubject(@NotNull UUID subject);
	
}