package org.kratos.backend.common.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.kratos.backend.common.constants.ResponseStatus;
import org.kratos.backend.common.dtos.Response;
import org.kratos.backend.common.dtos.ValidationErrorDetails;
import org.kratos.backend.common.dtos.ValidationErrorDetails.SimpleFieldError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler (BaseException.class)
	public ResponseEntity<Response<Object>> handleBusinessException(
			BaseException ex,
			HttpServletRequest request) {
		
		log.warn("Business exception: {} - Path: {}", ex.getMessage(), request.getRequestURI());
		
		Response<Object> response = Response.builder()
		                                    .status(ex.getResponseStatus())
		                                    .build();
		
		return ResponseEntity.badRequest()
		                     .body(response);
	}
	
	@ExceptionHandler ({MethodArgumentNotValidException.class, BindException.class})
	public ResponseEntity<Response<Object>> handleValidationErrors(
			Exception ex,
			HttpServletRequest request) {
		
		log.warn("Validation error: {} - Path: {}", ex.getMessage(), request.getRequestURI());
		
		List<SimpleFieldError> fieldErrors = getSimpleFieldErrors(ex);
		
		ValidationErrorDetails validationDetails = ValidationErrorDetails.builder()
		                                                                 .fieldErrors(fieldErrors)
		                                                                 .build();
		
		Response<Object> response = Response.builder()
		                                    .status(ResponseStatus.VALIDATION_ERROR)
		                                    .data(validationDetails)
		                                    .build();
		
		return ResponseEntity.badRequest()
		                     .body(response);
	}
	
	@ExceptionHandler (RuntimeException.class)
	public ResponseEntity<Response<Object>> handleRuntimeException(
			RuntimeException ex,
			HttpServletRequest request) {
		
		log.error("Unhandled runtime exception: {} - Path: {}", ex.getMessage(), request.getRequestURI(), ex);
		
		Response<Object> response = Response.builder()
		                                    .status(ResponseStatus.INTERNAL_ERROR)
		                                    .build();
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                     .body(response);
	}
	
	private static @NotNull List<SimpleFieldError> getSimpleFieldErrors(Exception ex) {
		List<SimpleFieldError> fieldErrors = List.of();
		if (ex instanceof MethodArgumentNotValidException validationEx) {
			fieldErrors = validationEx.getBindingResult()
			                          .getFieldErrors()
			                          .stream()
			                          .map(error -> SimpleFieldError.builder()
			                                                        .field(error.getField())
			                                                        .message(error.getDefaultMessage())
			                                                        .rejectedValue(error.getRejectedValue())
			                                                        .build())
			                          .collect(Collectors.toList());
		}
		return fieldErrors;
	}
}