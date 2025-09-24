package org.kratos.backend.form.dto;

import java.util.Map;

public record FormCreateRequest(@com.fasterxml.jackson.annotation.JsonProperty("formJson")
                                Map<String,Object> formJson) {}

