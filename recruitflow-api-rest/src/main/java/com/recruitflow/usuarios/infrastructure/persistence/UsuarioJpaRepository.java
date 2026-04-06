package com.recruitflow.usuarios.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link UsuarioJpaEntity}.
 */
public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpaEntity, UUID> {

  /**
   * Finds a user by tenant and id.
   *
   * @param companyId the tenant identifier
   * @param id        the user's unique identifier
   * @return Optional with the JPA entity if found
   */
  Optional<UsuarioJpaEntity> findByCompanyIdAndId(UUID companyId, UUID id);

  /**
   * Returns all users for a given tenant.
   *
   * @param companyId the tenant identifier
   * @return list of JPA entities
   */
  List<UsuarioJpaEntity> findAllByCompanyId(UUID companyId);
}
