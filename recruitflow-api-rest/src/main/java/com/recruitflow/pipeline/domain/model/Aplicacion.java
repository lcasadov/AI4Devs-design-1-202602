package com.recruitflow.pipeline.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Domain entity representing a candidate application (candidatura) — the pipeline entity.
 *
 * <p>An {@code Aplicacion} is the central entity of the recruitment pipeline.
 * It is not a simple join between a candidate and a position: it owns the current
 * stage, the matching score, and the discard reason.</p>
 *
 * <p>Pure domain object — no JPA annotations.</p>
 */
public class Aplicacion {

  private UUID id;
  private UUID companyId;
  private UUID positionId;
  private UUID candidateId;
  private UUID recruiterId;

  /**
   * Current pipeline stage.
   * Values: shortlisted / contacted / internal_interview / proposed /
   * client_interview / offer / hired / discarded
   */
  private String stage;

  /** Matching score (0-100). Contextual — same candidate may score differently per position. */
  private BigDecimal matchScore;

  /** JSON string breakdown of the score per skill. */
  private String matchDetail;

  /** Required if stage = discarded. */
  private String discardReason;

  /** Source of the application: matching / manual / job_board / referral. */
  private String source;

  private Instant createdAt;
  private Instant updatedAt;

  /** Default constructor. */
  public Aplicacion() {
  }

  /** @return the application's unique identifier */
  public UUID getId() { return id; }

  /** @param id the identifier to set */
  public void setId(UUID id) { this.id = id; }

  /** @return the tenant company identifier */
  public UUID getCompanyId() { return companyId; }

  /** @param companyId the tenant company identifier to set */
  public void setCompanyId(UUID companyId) { this.companyId = companyId; }

  /** @return the position identifier */
  public UUID getPositionId() { return positionId; }

  /** @param positionId the position identifier to set */
  public void setPositionId(UUID positionId) { this.positionId = positionId; }

  /** @return the candidate identifier */
  public UUID getCandidateId() { return candidateId; }

  /** @param candidateId the candidate identifier to set */
  public void setCandidateId(UUID candidateId) { this.candidateId = candidateId; }

  /** @return the recruiter identifier */
  public UUID getRecruiterId() { return recruiterId; }

  /** @param recruiterId the recruiter identifier to set */
  public void setRecruiterId(UUID recruiterId) { this.recruiterId = recruiterId; }

  /** @return the current pipeline stage */
  public String getStage() { return stage; }

  /** @param stage the stage to set */
  public void setStage(String stage) { this.stage = stage; }

  /** @return the matching score (0-100) */
  public BigDecimal getMatchScore() { return matchScore; }

  /** @param matchScore the match score to set */
  public void setMatchScore(BigDecimal matchScore) { this.matchScore = matchScore; }

  /** @return the JSON match detail breakdown */
  public String getMatchDetail() { return matchDetail; }

  /** @param matchDetail the match detail JSON to set */
  public void setMatchDetail(String matchDetail) { this.matchDetail = matchDetail; }

  /** @return the discard reason; null unless stage is discarded */
  public String getDiscardReason() { return discardReason; }

  /** @param discardReason the discard reason to set */
  public void setDiscardReason(String discardReason) { this.discardReason = discardReason; }

  /** @return the source of the application */
  public String getSource() { return source; }

  /** @param source the source to set */
  public void setSource(String source) { this.source = source; }

  /** @return the creation timestamp */
  public Instant getCreatedAt() { return createdAt; }

  /** @param createdAt the creation timestamp to set */
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

  /** @return the last update timestamp */
  public Instant getUpdatedAt() { return updatedAt; }

  /** @param updatedAt the update timestamp to set */
  public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
