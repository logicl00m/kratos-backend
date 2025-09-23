package org.kratos.backend.form.service.impl;


import lombok.RequiredArgsConstructor;
import org.kratos.backend.common.dtos.PaginatedResponse;
import org.kratos.backend.common.dtos.PaginationRequest;
import org.kratos.backend.common.dtos.PaginationResponse;
import org.kratos.backend.common.exceptions.BaseException;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.form.data.entities.Form;
import org.kratos.backend.form.data.repositories.FormRepository;
import org.kratos.backend.form.dto.FormResponse;
import org.kratos.backend.form.dto.FormUpdateRequest;
import org.kratos.backend.form.dto.FormCreateRequest;
import org.kratos.backend.form.mapper.FormMapper;
import org.kratos.backend.form.service.FormService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {
	
	private final FormRepository formRepository;
	private final FormMapper formMapper;
	
	public FormResponse create(String userId, FormCreateRequest request) {
		try {
			Form entity = formMapper.toEntity(request, userId);
			formRepository.save(entity);
			return formMapper.toResponse(entity);
		} catch (Exception e) {
			throw BaseException.builder()
			                   .responseStatus(ResponseStatus.FAILED_TO_CREATE_FORM)
			                   .build();
		}
	}
	
	@Override
	public FormResponse update(String userId, FormUpdateRequest request) {
		
		getEntity(request.id());
		var entity = formMapper.toEntity(request, userId);
		formRepository.saveAndFlush(entity);
		return formMapper.toResponse(entity);
	}
	
	@Override
	public FormResponse get(UUID formId) {
		Form form = getEntity(formId);
		return formMapper.toResponse(form);
	}
	
	@Override
	public Form getEntity(UUID formId) {
		return formRepository.findById(formId)
		                     .orElseThrow(() -> BaseException.builder()
		                                                     .responseStatus(ResponseStatus.FORM_NOT_FOUND)
		                                                     .build());
	}
	
	@Override
	public PaginatedResponse<List<FormResponse>> getAll(PaginationRequest page) {
		Pageable pageable = PageRequest.of(page.number(), page.size());
		Page<Form> list = formRepository.findAll(pageable);
		return new PaginatedResponse<>(formMapper.toResponseList(list.getContent()), new PaginationResponse(list));
	}
	
	@Override
	public void delete(String subject, UUID formId) {
		var form = getEntity(formId);
		form.setIsDeleted(true);
		formRepository.save(form);
	}
}
