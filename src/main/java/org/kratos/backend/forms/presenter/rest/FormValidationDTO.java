package org.kratos.backend.forms.presenter.rest;

public record FormValidationDTO(Boolean required, Number min, Number max, String regex) {}