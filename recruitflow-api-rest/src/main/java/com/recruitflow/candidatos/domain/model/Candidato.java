package com.recruitflow.candidatos.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Domain entity representing a candidate in the talent pool.
 *
 * <p>Pure domain object — no JPA annotations. Persistence mappings live in
 * {@code candidatos.infrastructure.persistence.CandidatoJpaEntity}.</p>
 */
public class Candidato {

  private UUID id;
  private UUID companyId;
  private String email;
  private String phone;
  private String fullName;
  private String location;
  private Short yearsExperience;
  private String availability;
  private BigDecimal salaryExpectation;
  private String cvUrl;
  private String linkedinUrl;
  private String source;
  private boolean gdprConsent;
  private Instant gdprConsentAt;
  private Instant anonymizedAt;
  private Instant createdAt;

  /** Default constructor. */
  public Candidato() {
  }

  // -------------------------------------------------------------------------
  // Getters and setters
  // -------------------------------------------------------------------------

  /** @return the candidate's unique identifier */
  public UUID getId() { return id; }

  /** @param id the identifier to set */
  public void setId(UUID id) { this.id = id; }

  /** @return the tenant identifier */
  public UUID getCompanyId() { return companyId; }

  /** @param companyId the tenant identifier to set */
  public void setCompanyId(UUID companyId) { this.companyId = companyId; }

  /** @return the candidate's email address */
  public String getEmail() { return email; }

  /** @param email the email to set */
  public void setEmail(String email) { this.email = email; }

  /** @return the candidate's phone number */
  public String getPhone() { return phone; }

  /** @param phone the phone to set */
  public void setPhone(String phone) { this.phone = phone; }

  /** @return the candidate's full name */
  public String getFullName() { return fullName; }

  /** @param fullName the full name to set */
  public void setFullName(String fullName) { this.fullName = fullName; }

  /** @return the candidate's location */
  public String getLocation() { return location; }

  /** @param location the location to set */
  public void setLocation(String location) { this.location = location; }

  /** @return total years of experience */
  public Short getYearsExperience() { return yearsExperience; }

  /** @param yearsExperience the years of experience to set */
  public void setYearsExperience(Short yearsExperience) { this.yearsExperience = yearsExperience; }

  /** @return availability status */
  public String getAvailability() { return availability; }

  /** @param availability the availability to set */
  public void setAvailability(String availability) { this.availability = availability; }

  /** @return the candidate's salary expectation */
  public BigDecimal getSalaryExpectation() { return salaryExpectation; }

  /** @param salaryExpectation the salary expectation to set */
  public void setSalaryExpectation(BigDecimal salaryExpectation) { this.salaryExpectation = salaryExpectation; }

  /** @return URL to the stored CV */
  public String getCvUrl() { return cvUrl; }

  /** @param cvUrl the CV URL to set */
  public void setCvUrl(String cvUrl) { this.cvUrl = cvUrl; }

  /** @return LinkedIn profile URL */
  public String getLinkedinUrl() { return linkedinUrl; }

  /** @param linkedinUrl the LinkedIn URL to set */
  public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }

  /** @return the source of the candidate */
  public String getSource() { return source; }

  /** @param source the source to set */
  public void setSource(String source) { this.source = source; }

  /** @return true if GDPR consent was given */
  public boolean isGdprConsent() { return gdprConsent; }

  /** @param gdprConsent the GDPR consent flag to set */
  public void setGdprConsent(boolean gdprConsent) { this.gdprConsent = gdprConsent; }

  /** @return timestamp of GDPR consent */
  public Instant getGdprConsentAt() { return gdprConsentAt; }

  /** @param gdprConsentAt the consent timestamp to set */
  public void setGdprConsentAt(Instant gdprConsentAt) { this.gdprConsentAt = gdprConsentAt; }

  /** @return timestamp of anonymization, or null if not anonymized */
  public Instant getAnonymizedAt() { return anonymizedAt; }

  /** @param anonymizedAt the anonymization timestamp to set */
  public void setAnonymizedAt(Instant anonymizedAt) { this.anonymizedAt = anonymizedAt; }

  /** @return timestamp when the candidate was added to the talent pool */
  public Instant getCreatedAt() { return createdAt; }

  /** @param createdAt the creation timestamp to set */
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
