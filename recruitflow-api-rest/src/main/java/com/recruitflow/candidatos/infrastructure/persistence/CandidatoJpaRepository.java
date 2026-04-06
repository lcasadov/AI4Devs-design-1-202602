package com.recruitflow.candidatos.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link CandidatoJpaEntity}.
 */
public interface CandidatoJpaRepository extends JpaRepository<CandidatoJpaEntity, UUID> {

  /**
   * Finds a candidate by tenant and id.
   *
   * @param companyId the tenant identifier
   * @param id        the candidate's unique identifier
   * @return Optional containing the JPA entity if found
   */
  Optional<CandidatoJpaEntity> findByCompanyIdAndId(UUID companyId, UUID id);

  /**
   * Returns all candidates for a given tenant.
   *
   * @param companyId the tenant identifier
   * @return list of JPA entities
   */
  List<CandidatoJpaEntity> findAllByCompanyId(UUID companyId);
}
