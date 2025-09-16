package org.kratos.backend.common.dtos;

public record PaginatedResponse <T>(T data, PaginationResponse pagination) {

}
