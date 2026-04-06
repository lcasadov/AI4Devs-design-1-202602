package com.recruitflow.pipeline.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Request DTO for creating a new application (candidatura).
 *
 * @param positionId  the position the candidate is applying to
 * @param candidateId the candidate
 * @param recruiterId the recruiter managing this application
 * @param source      source of the application: matching, manual, job_board, referral
 */
@Schema(description = "Request payload for creating an application (candidatura)")
public record AplicacionRequestDto(

    @Schema(description = "Position UUID", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull
    UUID positionId,

    @Schema(description = "Candidate UUID", example = "550e8400-e29b-41d4-a716-446655440001")
    @NotNull
    UUID candidateId,

    @Schema(description = "Recruiter UUID", example = "550e8400-e29b-41d4-a716-446655440002")
    @NotNull
    UUID recruiterId,

    @Schema(description = "Application source",
        allowableValues = {"matching", "manual", "job_board", "referral"})
    @NotNull
    String source
) {
}
