package com.recruitflow.vacantes.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA entity for the {@code positions} table.
 *
 * <p>This class belongs to the infrastructure layer and must not be referenced from
 * the domain or application layers. Use the domain model {@code Vacante} within the domain.</p>
 */
@Entity
@Table(name = "positions")
@Getter
@Setter
public class VacanteJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "code", unique = true, nullable = false, length = 30)
  private String code;

  @Column(name = "company_id", nullable = false)
  private UUID companyId;

  @Column(name = "client_id", nullable = false)
  private UUID clientId;

  @Column(name = "recruiter_id", nullable = false)
  private UUID recruiterId;

  @Column(name = "title", nullable = false, length = 255)
  private String title;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "location", length = 255)
  private String location;

  @Column(name = "modality", nullable = false, length = 20)
  private String modality;

  @Column(name = "salary_min", precision = 12, scale = 2)
  private BigDecimal salaryMin;

  @Column(name = "salary_max", precision = 12, scale = 2)
  private BigDecimal salaryMax;

  @Column(name = "status", nullable = false, length = 30)
  private String status;

  @Column(name = "priority", nullable = false, length = 20)
  private String priority;

  @Column(name = "deadline")
  private LocalDate deadline;

  @Column(name = "opened_at")
  private Instant openedAt;

  @Column(name = "closed_at")
  private Instant closedAt;
}
