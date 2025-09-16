package org.kratos.backend.auth.mapper;

import org.kratos.backend.auth.data.entities.AppRole;
import org.kratos.backend.auth.dto.AppRoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper (componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppRoleMapper {
	
	AppRoleResponse toResponse(AppRole appRole);
	
	Set<AppRoleResponse> toResponseList(Set<AppRole> facilities);
}
