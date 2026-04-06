package com.recruitflow.candidatos.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Request DTO for creating or updating a candidato.
 *
 * @param email             candidate's email address (deduplication index)
 * @param phone             candidate's phone number
 * @param fullName          candidate's full name
 * @param location          city or country of residence
 * @param yearsExperience   total years of professional experience
 * @param availability      availability: immediate, two_weeks, one_month, not_available
 * @param salaryExpectation salary expectation
 * @param linkedinUrl       LinkedIn profile URL
 * @param source            candidate source: linkedin, cv_upload, referral, job_board, manual
 * @param gdprConsent       GDPR consent flag (required)
 */
@Schema(description = "Request payload for creating or updating a candidate")
public record CandidatoRequestDto(

    @Schema(description = "Candidate email", example = "john.doe@example.com")
    @NotBlank
    @Email
    @Size(max = 255)
    String email,

    @Schema(description = "Candidate phone", example = "+34 600 000 000")
    @Size(max = 50)
    String phone,

    @Schema(description = "Full name", example = "John Doe")
    @NotBlank
    @Size(max = 255)
    String fullName,

    @Schema(description = "City or country of residence", example = "Madrid, Spain")
    @Size(max = 255)
    String location,

    @Schema(description = "Total years of professional experience", example = "5")
    Short yearsExperience,

    @Schema(description = "Availability",
        allowableValues = {"immediate", "two_weeks", "one_month", "not_available"})
    @NotBlank
    String availability,

    @Schema(description = "Salary expectation", example = "50000.00")
    BigDecimal salaryExpectation,

    @Schema(description = "LinkedIn profile URL", example = "https://linkedin.com/in/johndoe")
    @Size(max = 500)
    String linkedinUrl,

    @Schema(description = "Candidate source",
        allowableValues = {"linkedin", "cv_upload", "referral", "job_board", "manual"})
    @NotBlank
    String source,

    @Schema(description = "GDPR consent — must be true to create a candidate")
    @NotNull
    Boolean gdprConsent
) {
}
