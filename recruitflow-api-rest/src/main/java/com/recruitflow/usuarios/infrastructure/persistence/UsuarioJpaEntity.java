package com.recruitflow.usuarios.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA entity for the {@code users} table.
 *
 * <p>Infrastructure layer only.</p>
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class UsuarioJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "company_id", nullable = false)
  private UUID companyId;

  @Column(name = "email", unique = true, nullable = false, length = 255)
  private String email;

  @Column(name = "full_name", nullable = false, length = 255)
  private String fullName;

  @Column(name = "role", nullable = false, length = 20)
  private String role;

  @Column(name = "avatar_url", length = 500)
  private String avatarUrl;

  @Column(name = "is_active", nullable = false)
  private boolean active;

  @Column(name = "last_login_at")
  private Instant lastLoginAt;
}
