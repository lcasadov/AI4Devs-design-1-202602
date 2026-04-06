package com.recruitflow.vacantes.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Request DTO for creating or updating a vacante (position).
 *
 * <p>Validated by Bean Validation at the controller layer before the service is invoked.</p>
 *
 * @param clientId    identifier of the client company requesting the profile
 * @param recruiterId identifier of the recruiter responsible for the position
 * @param title       job title
 * @param description full job description
 * @param location    city or country
 * @param modality    work modality: presencial, remoto, hibrido
 * @param salaryMin   minimum salary range (nullable)
 * @param salaryMax   maximum salary range (nullable)
 * @param priority    priority level: low, medium, high, urgent
 * @param deadline    date by which the position should be filled
 */
@Schema(description = "Request payload for creating or updating a position")
public record VacanteRequestDto(

    @Schema(description = "Client company UUID", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull
    UUID clientId,

    @Schema(description = "Recruiter UUID", example = "550e8400-e29b-41d4-a716-446655440001")
    @NotNull
    UUID recruiterId,

    @Schema(description = "Job title", example = "Senior Java Developer", maxLength = 255)
    @NotBlank
    @Size(max = 255)
    String title,

    @Schema(description = "Full job description")
    @NotBlank
    String description,

    @Schema(description = "City or country", example = "Madrid, Spain", maxLength = 255)
    @Size(max = 255)
    String location,

    @Schema(description = "Work modality", allowableValues = {"presencial", "remoto", "hibrido"})
    @NotBlank
    String modality,

    @Schema(description = "Minimum salary", example = "40000.00")
    BigDecimal salaryMin,

    @Schema(description = "Maximum salary", example = "60000.00")
    BigDecimal salaryMax,

    @Schema(description = "Priority", allowableValues = {"low", "medium", "high", "urgent"})
    @NotBlank
    String priority,

    @Schema(description = "Deadline to fill the position", example = "2026-06-30")
    LocalDate deadline
) {
}
