package com.recruitflow.vacantes.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link VacanteJpaEntity}.
 *
 * <p>This interface is part of the infrastructure layer. Business logic must not
 * be added here — query results are translated to domain objects by
 * {@link VacanteRepositoryAdapter}.</p>
 */
public interface VacanteJpaRepository extends JpaRepository<VacanteJpaEntity, UUID> {

  /**
   * Finds a position by tenant and id.
   *
   * @param companyId the tenant identifier
   * @param id        the position's unique identifier
   * @return an Optional containing the JPA entity if found
   */
  Optional<VacanteJpaEntity> findByCompanyIdAndId(UUID companyId, UUID id);

  /**
   * Returns all positions for a given tenant.
   *
   * @param companyId the tenant identifier
   * @return list of JPA entities
   */
  List<VacanteJpaEntity> findAllByCompanyId(UUID companyId);
}
