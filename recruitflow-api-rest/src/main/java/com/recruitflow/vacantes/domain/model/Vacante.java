package com.recruitflow.vacantes.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Domain entity representing an open position (vacante / Position).
 *
 * <p>This is a pure domain object — it has no JPA annotations and does not depend
 * on any infrastructure. Persistence mappings live in
 * {@code vacantes.infrastructure.persistence.VacanteJpaEntity}.</p>
 */
public class Vacante {

  /** Unique identifier. */
  private UUID id;

  /** Auto-generated code (e.g. {@code POS-2026-0042}). */
  private String code;

  /** Tenant that owns this position. */
  private UUID companyId;

  /** Client company requesting the profile. */
  private UUID clientId;

  /** Recruiter responsible for filling this position. */
  private UUID recruiterId;

  /** Job title. */
  private String title;

  /** Full job description. */
  private String description;

  /** City / Country. */
  private String location;

  /** Work modality: presencial, remoto, hibrido. */
  private String modality;

  /** Minimum salary range. */
  private BigDecimal salaryMin;

  /** Maximum salary range. */
  private BigDecimal salaryMax;

  /** Status: draft / active / in_progress / closed_filled / closed_cancelled. */
  private String status;

  /** Priority: low / medium / high / urgent. */
  private String priority;

  /** Deadline to fill the position. */
  private LocalDate deadline;

  /** Timestamp when the position was opened. */
  private Instant openedAt;

  /** Timestamp when the position was closed; null if still open. */
  private Instant closedAt;

  /** Default constructor required by frameworks. */
  public Vacante() {
  }

  // -------------------------------------------------------------------------
  // Getters and setters (no Lombok on domain objects — no infrastructure deps)
  // -------------------------------------------------------------------------

  /** @return the position's unique identifier */
  public UUID getId() { return id; }

  /** @param id the unique identifier to set */
  public void setId(UUID id) { this.id = id; }

  /** @return the auto-generated position code */
  public String getCode() { return code; }

  /** @param code the code to set */
  public void setCode(String code) { this.code = code; }

  /** @return the tenant company identifier */
  public UUID getCompanyId() { return companyId; }

  /** @param companyId the tenant identifier to set */
  public void setCompanyId(UUID companyId) { this.companyId = companyId; }

  /** @return the client company identifier */
  public UUID getClientId() { return clientId; }

  /** @param clientId the client identifier to set */
  public void setClientId(UUID clientId) { this.clientId = clientId; }

  /** @return the responsible recruiter's identifier */
  public UUID getRecruiterId() { return recruiterId; }

  /** @param recruiterId the recruiter identifier to set */
  public void setRecruiterId(UUID recruiterId) { this.recruiterId = recruiterId; }

  /** @return the job title */
  public String getTitle() { return title; }

  /** @param title the job title to set */
  public void setTitle(String title) { this.title = title; }

  /** @return the full job description */
  public String getDescription() { return description; }

  /** @param description the description to set */
  public void setDescription(String description) { this.description = description; }

  /** @return the location */
  public String getLocation() { return location; }

  /** @param location the location to set */
  public void setLocation(String location) { this.location = location; }

  /** @return the work modality */
  public String getModality() { return modality; }

  /** @param modality the modality to set */
  public void setModality(String modality) { this.modality = modality; }

  /** @return the minimum salary */
  public BigDecimal getSalaryMin() { return salaryMin; }

  /** @param salaryMin the minimum salary to set */
  public void setSalaryMin(BigDecimal salaryMin) { this.salaryMin = salaryMin; }

  /** @return the maximum salary */
  public BigDecimal getSalaryMax() { return salaryMax; }

  /** @param salaryMax the maximum salary to set */
  public void setSalaryMax(BigDecimal salaryMax) { this.salaryMax = salaryMax; }

  /** @return the current status */
  public String getStatus() { return status; }

  /** @param status the status to set */
  public void setStatus(String status) { this.status = status; }

  /** @return the priority level */
  public String getPriority() { return priority; }

  /** @param priority the priority to set */
  public void setPriority(String priority) { this.priority = priority; }

  /** @return the deadline date */
  public LocalDate getDeadline() { return deadline; }

  /** @param deadline the deadline to set */
  public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

  /** @return the timestamp when the position was opened */
  public Instant getOpenedAt() { return openedAt; }

  /** @param openedAt the opened timestamp to set */
  public void setOpenedAt(Instant openedAt) { this.openedAt = openedAt; }

  /** @return the timestamp when the position was closed, or null if still open */
  public Instant getClosedAt() { return closedAt; }

  /** @param closedAt the closed timestamp to set */
  public void setClosedAt(Instant closedAt) { this.closedAt = closedAt; }
}
