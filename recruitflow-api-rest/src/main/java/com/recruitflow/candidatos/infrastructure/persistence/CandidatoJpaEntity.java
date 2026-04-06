package com.recruitflow.candidatos.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA entity for the {@code candidates} table.
 *
 * <p>Infrastructure layer only — never referenced from domain or application layers.</p>
 */
@Entity
@Table(name = "candidates")
@Getter
@Setter
public class CandidatoJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "company_id", nullable = false)
  private UUID companyId;

  @Column(name = "email", length = 255)
  private String email;

  @Column(name = "phone", length = 50)
  private String phone;

  @Column(name = "full_name", length = 255)
  private String fullName;

  @Column(name = "location", length = 255)
  private String location;

  @Column(name = "years_experience")
  private Short yearsExperience;

  @Column(name = "availability", length = 20)
  private String availability;

  @Column(name = "salary_expectation", precision = 12, scale = 2)
  private BigDecimal salaryExpectation;

  @Column(name = "cv_url", length = 500)
  private String cvUrl;

  @Column(name = "linkedin_url", length = 500)
  private String linkedinUrl;

  @Column(name = "source", length = 30)
  private String source;

  @Column(name = "gdpr_consent", nullable = false)
  private boolean gdprConsent;

  @Column(name = "gdpr_consent_at")
  private Instant gdprConsentAt;

  @Column(name = "anonymized_at")
  private Instant anonymizedAt;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
}
