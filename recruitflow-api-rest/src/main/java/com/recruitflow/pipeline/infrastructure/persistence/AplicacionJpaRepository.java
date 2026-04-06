package com.recruitflow.pipeline.infrastructure.persistence;

import java.util.List;
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
}
