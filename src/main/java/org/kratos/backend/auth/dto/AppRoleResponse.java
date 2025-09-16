package org.kratos.backend.auth.dto;

import java.util.UUID;

public record AppRoleResponse(UUID id, String appRole, Boolean isPredefined) {

}
