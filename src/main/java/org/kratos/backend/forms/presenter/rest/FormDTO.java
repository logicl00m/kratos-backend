package org.kratos.backend.forms.presenter.rest;

import java.util.Map;

public record FormDTO(
    String id,
    String name,
    Integer version,
    Map<String, FormSection> json
) {
  // Extended projection for GET one
  public FormDTO(String id, String name, Integer version, Map<String, FormSection> json, String createdAt, String updatedAt) {
    this(id, name, version, json);
    // createdAt/updatedAt emitted in wrapper below
  }
}