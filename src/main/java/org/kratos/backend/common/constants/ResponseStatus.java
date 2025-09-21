package org.kratos.backend.common.constants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public class ResponseStatus {
	
	// Success
	public static final ResponseStatus ALL_OK = new ResponseStatus("S2000", "operation successful");
	
	// Client Errors (4xxx)
	public static final ResponseStatus VALIDATION_ERROR = new ResponseStatus("E4001", "request body validation failed");
	public static final ResponseStatus MISSING_SUBJECT_UUID = new ResponseStatus("E4002",
	                                                                             "subject id is missing");
	public static final ResponseStatus INVALID_SUBJECT_UUID = new ResponseStatus("E4002",
	                                                                             "subject id is an invalid uuid");
	public static final ResponseStatus WF_CONFIG_NOT_FOUND = new ResponseStatus("E4003",
	                                                                            "workflow configuration not found");
	public static final ResponseStatus FAILED_TO_LOAD_WF_SCHEMA = new ResponseStatus("E4004",
	                                                                            "failed to load wf schema");
	
	// System Errors (5xxx)
	public static final ResponseStatus INTERNAL_ERROR = new ResponseStatus("E5002", "internal error");
	
	final String status;
	
	@JsonIgnore
	final String message;
	
	ResponseStatus(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	@JsonValue
	public String getJSONValue() {
		return this.status;
	}
}
