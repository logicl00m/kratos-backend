package org.kratos.backend.auth.service;

import org.kratos.backend.auth.constants.PredefinedRoles;
import org.kratos.backend.auth.data.entities.AppRole;
import org.kratos.backend.auth.dto.ResourceOperation;
import org.kratos.backend.auth.dto.AppRoleResponse;
import org.kratos.backend.auth.dto.ResourceOperationScope;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ResourceOperationService {
	
	boolean checkPermission(String userId, UUID subject, ResourceOperationScope resourceOperationScope);
	
	List<UUID> getSubjects(String userId);
	
	List<UUID> getAllSubjects(String userId);
	
	Set<ResourceOperation> getResourceOperations(String userId, UUID subject);
	
	void assignRoleBySubject(String userId, UUID subject, UUID roleId, Boolean status);
	
	AppRole getPredefinedRoleByName(PredefinedRoles predefinedRole);
	
	Set<AppRoleResponse> getRoles(String userId, UUID subject);
	
	Set<AppRoleResponse> getAllRoles(String userId, UUID subject);
	
	void removeRolesByUserFromSubject(String userId, UUID facilityId);
	
	void removeRolesFromSubject(UUID subject);
}
