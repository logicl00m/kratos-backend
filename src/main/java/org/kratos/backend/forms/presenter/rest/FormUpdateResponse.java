package org.kratos.backend.forms.presenter.rest;

import java.util.Map;

public record FormUpdateResponse(Updated data, String status) {
  public static record Updated(String id, String name, Integer version, Map<String, FormSection> json, String updatedAt) {}
}