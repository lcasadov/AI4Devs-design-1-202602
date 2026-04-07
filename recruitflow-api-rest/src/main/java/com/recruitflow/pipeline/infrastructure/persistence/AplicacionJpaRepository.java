package com.recruitflow.pipeline.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link AplicacionJpaEntity}.
 */
public interface AplicacionJpaRepository extends JpaRepository<AplicacionJpaEntity, UUID> {

  /**
   * Returns all applications for a given position.
   *
   * @param positionId the position's unique identifier
   * @return list of JPA entities
   */
  List<AplicacionJpaEntity> findAllByPositionId(UUID positionId);

  /**
   * Finds an application by id scoped to a specific tenant.
   *
   * @param id        the application's unique identifier
   * @param companyId the tenant identifier
   * @return Optional JPA entity if found and belongs to the tenant
   */
  Optional<AplicacionJpaEntity> findByIdAndCompanyId(UUID id, UUID companyId);
}
