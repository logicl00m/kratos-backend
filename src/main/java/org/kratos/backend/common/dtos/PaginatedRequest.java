package org.kratos.backend.common.dtos;

import jakarta.validation.Valid;

public record PaginatedRequest <T>(@Valid T data, @Valid PaginationRequest pagination) {

}
