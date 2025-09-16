package org.kratos.backend.common.dtos;

import org.springframework.data.domain.Page;

public record PaginationResponse(Integer currentPage, Integer pageSize, Integer totalPages, Long totalItems) {
	
	public PaginationResponse(Page<?> page) {
		this(page.getNumber(), page.getSize(), page.getTotalPages(), page.getTotalElements());
	}
}
