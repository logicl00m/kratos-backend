package org.kratos.backend.form.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record FormCreateRequest(@JsonProperty("formJson")
                                Map<String,Object> formJson) {

}
