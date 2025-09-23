package org.kratos.backend.form.dto;

import java.util.Map;
import java.util.UUID;
public record FormResponse(String formName, UUID id, Map<String, Object> configJson) {
}
