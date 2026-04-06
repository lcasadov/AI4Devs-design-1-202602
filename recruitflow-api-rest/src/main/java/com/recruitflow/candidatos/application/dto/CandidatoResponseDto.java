package com.recruitflow.candidatos.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Response DTO for a candidato.
 *
 * @param id                unique identifier
 * @param companyId         tenant identifier
 * @param email             candidate's email (masked if anonymized)
 * @param phone             candidate's phone
 * @param fullName          candidate's full name (masked if anonymized)
 * @param location          city or country
 * @param yearsExperience   total years of experience
 * @param availability      availability status
 * @param salaryExpectation salary expectation
 * @param cvUrl             URL to the stored CV
 * @param linkedinUrl       LinkedIn profile URL
 * @param source            candidate source
 * @param gdprConsent       GDPR consent flag
 * @param gdprConsentAt     timestamp of GDPR consent
 * @param anonymizedAt      timestamp of anonymization; null if not anonymized
 * @param createdAt         timestamp when the candidate was added
 */
@Schema(description = "Candidate response payload")
public record CandidatoResponseDto(

    @Schema(description = "Unique identifier")
    UUID id,

    @Schema(description = "Tenant identifier")
    UUID companyId,

    @Schema(description = "Candidate email")
    String email,

    @Schema(description = "Candidate phone")
    String phone,

    @Schema(description = "Full name")
    String fullName,

    @Schema(description = "City or country")
    String location,

    @Schema(description = "Total years of experience")
    Short yearsExperience,

    @Schema(description = "Availability",
        allowableValues = {"immediate", "two_weeks", "one_month", "not_available"})
    String availability,

    @Schema(description = "Salary expectation")
    BigDecimal salaryExpectation,

    @Schema(description = "URL to stored CV")
    String cvUrl,

    @Schema(description = "LinkedIn profile URL")
    String linkedinUrl,

    @Schema(description = "Candidate source")
    String source,

    @Schema(description = "GDPR consent")
    boolean gdprConsent,

    @Schema(description = "GDPR consent timestamp")
    Instant gdprConsentAt,

    @Schema(description = "Anonymization timestamp; null if not anonymized")
    Instant anonymizedAt,

    @Schema(description = "Creation timestamp")
    Instant createdAt
) {
}
