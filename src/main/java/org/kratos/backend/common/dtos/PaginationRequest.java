package org.kratos.backend.common.dtos;

import jakarta.validation.constraints.Min;

public record PaginationRequest(
		@Min (value = 0, message = "page must be at least 0")
		int number,
		
		@Min (value = 1, message = "size must be at least 1")
		int size) {
	
}
