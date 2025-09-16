package org.kratos.backend.common.dtos;

import org.kratos.backend.common.constants.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude (JsonInclude.Include.NON_NULL)
public class Response <T> implements Serializable {
	
	@NonNull
	ResponseStatus status;
	@NonNull
	String message;
	T data;
	PaginationResponse pagination;
	
	public static class ResponseBuilder <T> {
		
		public ResponseBuilder<T> status(ResponseStatus status) {
			this.status = status;
			this.message = status.getMessage();
			return this;
		}
	}
}
