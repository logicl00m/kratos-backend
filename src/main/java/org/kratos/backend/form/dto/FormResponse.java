package org.kratos.backend.form.dto;

import java.util.Map;
import java.util.UUID;

public record FormResponse(UUID id, String name, Map<String, Object> config) {

}
