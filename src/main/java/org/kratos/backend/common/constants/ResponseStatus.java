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
	public static final ResponseStatus WF_NOT_FOUND = new ResponseStatus("E4005",
	                                                                     "workflow not found");
	public static final ResponseStatus ACTION_NOT_FOUND_FOR_CURRENT_STATE = new ResponseStatus("E4006",
	                                                                                           "action not found for current state");
	
	public static final ResponseStatus WF_STATE_HANDLER_NOT_DEFINED = new ResponseStatus("E4007",
	                                                                                     "workflow state handler not defined");
	
	public static final ResponseStatus FAILED_TO_CREATE_FORM = new ResponseStatus("E4008",
	                                                                              "failed to create form");
	
	public static final ResponseStatus FORM_NOT_FOUND = new ResponseStatus("E4009",
	                                                                       "form not found");
	public static final ResponseStatus ACTION_VALIDATION_FAILED = new ResponseStatus("E4010",
	                                                                                 "action validation failed");
	public static final ResponseStatus SCRIPT_NOT_FOUND = new ResponseStatus("E4011",
	                                                                                 "script not found");
	
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
