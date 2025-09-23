package org.kratos.backend.form.dto;

// fixme(high): add form create config validation
public record FormCreateRequest(String name, java.util.Map<String, Object> config) {

}
