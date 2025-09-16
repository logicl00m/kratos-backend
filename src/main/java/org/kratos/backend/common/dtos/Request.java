package org.kratos.backend.common.dtos;

import jakarta.validation.Valid;

public record Request <T>(@Valid T data) {

}
