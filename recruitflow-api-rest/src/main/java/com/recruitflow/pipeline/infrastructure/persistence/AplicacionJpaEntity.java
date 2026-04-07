package com.recruitflow.pipeline.infrastructure.persistence;

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
 * JPA entity for the {@code applications} table.
 *
 * <p>Infrastructure layer only — never referenced from domain or application layers.</p>
 */
@Entity
@Table(name = "applications")
@Getter
@Setter
public class AplicacionJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "company_id", nullable = false)
  private UUID companyId;

  @Column(name = "position_id", nullable = false)
  private UUID positionId;

  @Column(name = "candidate_id", nullable = false)
  private UUID candidateId;

  @Column(name = "recruiter_id", nullable = false)
  private UUID recruiterId;

  @Column(name = "stage", nullable = false, length = 30)
  private String stage;

  @Column(name = "match_score", precision = 5, scale = 2)
  private BigDecimal matchScore;

  @Column(name = "match_detail", columnDefinition = "TEXT")
  private String matchDetail;

  @Column(name = "discard_reason", length = 500)
  private String discardReason;

  @Column(name = "source", nullable = false, length = 20)
  private String source;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;
}
