package com.recruitflow.pipeline.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for advancing or changing the pipeline stage of an application.
 *
 * @param stage         the new pipeline stage
 * @param notes         optional notes from the recruiter about the stage change
 * @param discardReason required when the new stage is {@code discarded}
 */
@Schema(description = "Request payload for changing the pipeline stage of an application")
public record CambioEtapaRequestDto(

    @Schema(description = "New pipeline stage",
        allowableValues = {"shortlisted", "contacted", "internal_interview", "proposed",
            "client_interview", "offer", "hired", "discarded"})
    @NotBlank
    String stage,

    @Schema(description = "Optional recruiter notes")
    @Size(max = 2000)
    String notes,

    @Schema(description = "Discard reason — required when stage is 'discarded'")
    @Size(max = 500)
    String discardReason
) {
}
