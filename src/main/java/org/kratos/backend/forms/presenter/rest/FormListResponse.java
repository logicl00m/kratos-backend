package org.kratos.backend.forms.presenter.rest;

import java.util.List;

public record FormListResponse(List<FormListItem> data, PageMeta pagination, String status) {}