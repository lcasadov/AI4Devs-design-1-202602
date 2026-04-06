package com.recruitflow.pipeline.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Response DTO for an application (candidatura).
 *
 * @param id            unique identifier
 * @param positionId    position identifier
 * @param candidateId   candidate identifier
 * @param recruiterId   recruiter identifier
 * @param stage         current pipeline stage
 * @param matchScore    matching score (0-100)
 * @param discardReason discard reason; null unless stage is discarded
 * @param source        source of the application
 * @param createdAt     creation timestamp
 * @param updatedAt     last update timestamp
 */
@Schema(description = "Application (candidatura) response payload")
public record AplicacionResponseDto(

    @Schema(description = "Unique identifier")
    UUID id,

    @Schema(description = "Position identifier")
    UUID positionId,

    @Schema(description = "Candidate identifier")
    UUID candidateId,

    @Schema(description = "Recruiter identifier")
    UUID recruiterId,

    @Schema(description = "Current pipeline stage",
        allowableValues = {"shortlisted", "contacted", "internal_interview", "proposed",
            "client_interview", "offer", "hired", "discarded"})
    String stage,

    @Schema(description = "Matching score 0-100", example = "87.5")
    BigDecimal matchScore,

    @Schema(description = "Discard reason; null unless stage is discarded")
    String discardReason,

    @Schema(description = "Source", allowableValues = {"matching", "manual", "job_board", "referral"})
    String source,

    @Schema(description = "Creation timestamp")
    Instant createdAt,

    @Schema(description = "Last update timestamp")
    Instant updatedAt
) {
}
