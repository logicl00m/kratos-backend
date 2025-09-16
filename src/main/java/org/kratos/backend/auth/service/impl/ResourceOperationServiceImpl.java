package org.kratos.backend.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.kratos.backend.auth.constants.PredefinedRoles;
import org.kratos.backend.auth.data.entities.AppRole;
import org.kratos.backend.auth.data.entities.SubjectRole;
import org.kratos.backend.auth.data.repositories.AppRoleRepository;
import org.kratos.backend.auth.data.repositories.SubjectRoleRepository;
import org.kratos.backend.auth.dto.AppRoleResponse;
import org.kratos.backend.auth.dto.ResourceOperation;
import org.kratos.backend.auth.dto.ResourceOperationScope;
import org.kratos.backend.auth.mapper.AppRoleMapper;
import org.kratos.backend.auth.service.ResourceOperationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceOperationServiceImpl implements ResourceOperationService {
	
	private final SubjectRoleRepository subjectRoleRepository;
	private final AppRoleRepository appRoleRepository;
	private final AppRoleMapper appRoleMapper;
	
	@Override
	public boolean checkPermission(String userId, UUID subject, ResourceOperationScope resourceOperationScope) {
		// if scope == common && user id == subject, then user has permission
		if (Objects.nonNull(resourceOperationScope.scope()) &&
				resourceOperationScope.scope()
				                      .equals("common") && userId.equals(subject.toString())) {
			log.info("allowing because scope is common and user id equals subject id");
			return true;
		}
		
		// get the resource operations from database
		Set<ResourceOperation> resourceOperations = getResourceOperationsFromDb(userId, subject);
		log.info("resource operations from db: {}", resourceOperations);
		
		// check if user has permission
		return resourceOperations.contains(resourceOperationScope.resourceOperation());
	}
	
	public @NotNull List<UUID> getSubjects(String userId) {
		return subjectRoleRepository.findByUserIdAndStatusTrue(userId)
		                            .stream()
		                            .map(SubjectRole::getSubject)
		                            .toList();
	}
	
	@Override
	public List<UUID> getAllSubjects(String userId) {
		return subjectRoleRepository.findByUserId(userId)
		                            .stream()
		                            .map(SubjectRole::getSubject)
		                            .toList();
	}
	
	@Override
	public @NotNull Set<ResourceOperation> getResourceOperations(String userId, UUID subject) {
		return getResourceOperationsFromDb(userId, subject);
	}
	
	@Override
	public void assignRoleBySubject(String userId, UUID subject, UUID roleId, Boolean status) {
		subjectRoleRepository.assignRoleBySubjectToUser(userId, subject, roleId, status);
	}
	
	@Override
	public AppRole getPredefinedRoleByName(PredefinedRoles predefinedRole) {
		return appRoleRepository.getAppRoleByAppRole(predefinedRole.name());
	}
	
	@Override
	public Set<AppRoleResponse> getRoles(String userId, UUID subject) {
		return appRoleMapper.toResponseList(subjectRoleRepository.findRolesByUserIdAndSubject(userId, subject));
	}
	
	@Override
	public Set<AppRoleResponse> getAllRoles(String userId, UUID subject) {
		return appRoleMapper.toResponseList(subjectRoleRepository.findAllRolesByUserIdAndSubject(userId, subject));
	}
	
	@Override
	public void removeRolesByUserFromSubject(String userId, UUID facilityId) {
		subjectRoleRepository.deleteAllByUserIdAndSubject(userId, facilityId);
	}
	
	@Override
	public void removeRolesFromSubject(UUID subject) {
		subjectRoleRepository.deleteAllBySubject(subject);
	}
	
	private @NotNull Set<ResourceOperation> getResourceOperationsFromDb(String userId, UUID subject) {
		return subjectRoleRepository
				.findResourceOperationsByUserIdAndSubject(userId, subject)
				.stream()
				.map(ro -> new ResourceOperation(ro.getResource(), ro.getOperation()))
				.collect(Collectors.toSet());
	}
}
