package com.recruitflow.vacantes.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Response DTO for a vacante (position).
 *
 * <p>Returned by all vacante endpoints. Domain entities are never returned directly
 * from the service layer — they are always converted to this DTO first.</p>
 *
 * @param id          unique identifier
 * @param code        auto-generated position code (e.g. POS-2026-0042)
 * @param companyId   tenant identifier
 * @param clientId    client company identifier
 * @param recruiterId recruiter identifier
 * @param title       job title
 * @param description full job description
 * @param location    city or country
 * @param modality    work modality
 * @param salaryMin   minimum salary range
 * @param salaryMax   maximum salary range
 * @param status      current status
 * @param priority    priority level
 * @param deadline    deadline to fill the position
 * @param openedAt    timestamp when the position was opened
 * @param closedAt    timestamp when the position was closed; null if still open
 */
@Schema(description = "Position (vacante) response payload")
public record VacanteResponseDto(

    @Schema(description = "Unique identifier")
    UUID id,

    @Schema(description = "Auto-generated position code", example = "POS-2026-0042")
    String code,

    @Schema(description = "Tenant identifier")
    UUID companyId,

    @Schema(description = "Client company identifier")
    UUID clientId,

    @Schema(description = "Recruiter identifier")
    UUID recruiterId,

    @Schema(description = "Job title", example = "Senior Java Developer")
    String title,

    @Schema(description = "Full job description")
    String description,

    @Schema(description = "City or country", example = "Madrid, Spain")
    String location,

    @Schema(description = "Work modality", allowableValues = {"presencial", "remoto", "hibrido"})
    String modality,

    @Schema(description = "Minimum salary", example = "40000.00")
    BigDecimal salaryMin,

    @Schema(description = "Maximum salary", example = "60000.00")
    BigDecimal salaryMax,

    @Schema(description = "Status",
        allowableValues = {"draft", "active", "in_progress", "closed_filled", "closed_cancelled"})
    String status,

    @Schema(description = "Priority", allowableValues = {"low", "medium", "high", "urgent"})
    String priority,

    @Schema(description = "Deadline to fill the position", example = "2026-06-30")
    LocalDate deadline,

    @Schema(description = "Timestamp when the position was opened")
    Instant openedAt,

    @Schema(description = "Timestamp when the position was closed; null if still open")
    Instant closedAt
) {
}
