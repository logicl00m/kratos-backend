package org.kratos.backend.common.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.kratos.backend.common.constants.ResponseStatus;

@Builder
@Getter
public class BaseException extends RuntimeException {
	
	protected final ResponseStatus responseStatus;
	protected final ExceptionData data;
	
	protected BaseException(@NotNull ResponseStatus responseStatus, ExceptionData data) {
		super(responseStatus.getMessage());
		this.responseStatus = responseStatus;
		this.data = data;
	}
}