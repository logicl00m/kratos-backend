package org.kratos.backend.form.mapper;

import org.kratos.backend.form.data.entities.Form;
import org.kratos.backend.form.dto.FormCreateRequest;
import org.kratos.backend.form.dto.FormResponse;
import org.kratos.backend.form.dto.FormUpdateRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FormMapper {
	
	@Mapping (target = "id", ignore = true)
	@Mapping (target = "createdBy", expression = "java(userId)")
	@Mapping (target = "updatedBy", expression = "java(userId)")
	Form toEntity(FormCreateRequest createRequest, @Context String userId);
	
	@Mapping (target = "createdBy", expression = "java(userId)")
	@Mapping (target = "updatedBy", expression = "java(userId)")
	Form toEntity(FormUpdateRequest updateRequest, @Context String userId);
	
	FormResponse toResponse(Form form);
	
	List<FormResponse> toResponseList(List<Form> forms);
}