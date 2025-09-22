package org.kratos.backend.forms.presenter.rest;

import java.util.List;

public record FormListItem(String id, String name, Integer version, String updatedAt) {}