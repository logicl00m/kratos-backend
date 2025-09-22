package org.kratos.backend.forms.presenter.rest;

import java.util.Map;

public record FormCreateResponse(Created data, String status) {
  public static record Created(String id, String name, Integer version, Map<String, FormSection> json, String createdAt, String updatedAt) {}
}