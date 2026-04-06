package com.recruitflow.usuarios.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for creating or updating a usuario.
 *
 * @param email    corporate email address
 * @param fullName user's full name
 * @param role     role: admin, manager, recruiter
 */
@Schema(description = "Request payload for creating or updating a user")
public record UsuarioRequestDto(

    @Schema(description = "Corporate email", example = "recruiter@company.com")
    @NotBlank
    @Email
    @Size(max = 255)
    String email,

    @Schema(description = "Full name", example = "Jane Smith")
    @NotBlank
    @Size(max = 255)
    String fullName,

    @Schema(description = "Role", allowableValues = {"admin", "manager", "recruiter"})
    @NotBlank
    String role
) {
}
