package org.kratos.backend.common.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationErrorDetails {
    private final List<SimpleFieldError> fieldErrors;
    
    @Getter
    @Builder
    public static class SimpleFieldError {
        private final String field;
        private final String message;
        private final Object rejectedValue;
    }
}