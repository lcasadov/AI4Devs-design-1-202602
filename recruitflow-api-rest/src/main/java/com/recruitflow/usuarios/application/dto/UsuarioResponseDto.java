package com.recruitflow.usuarios.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

/**
 * Response DTO for a usuario.
 *
 * @param id          unique identifier
 * @param companyId   tenant identifier
 * @param email       corporate email
 * @param fullName    full name
 * @param role        role: admin, manager, recruiter
 * @param avatarUrl   avatar URL
 * @param active      whether the account is active
 * @param lastLoginAt timestamp of last login
 */
@Schema(description = "User response payload")
public record UsuarioResponseDto(

    @Schema(description = "Unique identifier")
    UUID id,

    @Schema(description = "Tenant identifier")
    UUID companyId,

    @Schema(description = "Corporate email")
    String email,

    @Schema(description = "Full name")
    String fullName,

    @Schema(description = "Role", allowableValues = {"admin", "manager", "recruiter"})
    String role,

    @Schema(description = "Avatar URL")
    String avatarUrl,

    @Schema(description = "Account active flag")
    boolean active,

    @Schema(description = "Timestamp of last login")
    Instant lastLoginAt
) {
}
