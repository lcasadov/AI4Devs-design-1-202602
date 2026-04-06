package com.recruitflow.usuarios.domain.model;

import java.time.Instant;
import java.util.UUID;

/**
 * Domain entity representing an internal user (recruiter, manager, admin).
 *
 * <p>Pure domain object — no JPA annotations.</p>
 */
public class Usuario {

  private UUID id;
  private UUID companyId;
  private String email;
  private String fullName;
  private String role;
  private String avatarUrl;
  private boolean active;
  private Instant lastLoginAt;

  /** Default constructor. */
  public Usuario() {
  }

  /** @return the user's unique identifier */
  public UUID getId() { return id; }

  /** @param id the identifier to set */
  public void setId(UUID id) { this.id = id; }

  /** @return the tenant identifier */
  public UUID getCompanyId() { return companyId; }

  /** @param companyId the tenant identifier to set */
  public void setCompanyId(UUID companyId) { this.companyId = companyId; }

  /** @return the user's email address */
  public String getEmail() { return email; }

  /** @param email the email to set */
  public void setEmail(String email) { this.email = email; }

  /** @return the user's full name */
  public String getFullName() { return fullName; }

  /** @param fullName the full name to set */
  public void setFullName(String fullName) { this.fullName = fullName; }

  /** @return the user's role: admin, manager, recruiter */
  public String getRole() { return role; }

  /** @param role the role to set */
  public void setRole(String role) { this.role = role; }

  /** @return URL to the user's avatar */
  public String getAvatarUrl() { return avatarUrl; }

  /** @param avatarUrl the avatar URL to set */
  public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

  /** @return true if the user account is active */
  public boolean isActive() { return active; }

  /** @param active the active flag to set */
  public void setActive(boolean active) { this.active = active; }

  /** @return timestamp of last login */
  public Instant getLastLoginAt() { return lastLoginAt; }

  /** @param lastLoginAt the last login timestamp to set */
  public void setLastLoginAt(Instant lastLoginAt) { this.lastLoginAt = lastLoginAt; }
}
