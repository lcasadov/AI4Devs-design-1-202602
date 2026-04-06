package com.recruitflow.candidatos.domain.port.out;

import com.recruitflow.candidatos.domain.model.Candidato;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Output port (driven port) for candidato persistence.
 *
 * <p>Belongs to the domain layer. JPA implementation lives in
 * {@code candidatos.infrastructure.persistence.CandidatoRepositoryAdapter}.</p>
 */
public interface CandidatoRepository {

  /**
   * Persists a new or updated candidato.
   *
   * @param candidato the domain entity to persist
   * @return the persisted entity
   */
  Candidato save(Candidato candidato);

  /**
   * Finds a candidato by tenant and id.
   *
   * @param companyId the tenant identifier
   * @param id        the candidate's unique identifier
   * @return an Optional containing the candidato if found
   */
  Optional<Candidato> findByCompanyIdAndId(UUID companyId, UUID id);

  /**
   * Returns all candidatos for a tenant.
   *
   * @param companyId the tenant identifier
   * @return list of candidatos
   */
  List<Candidato> findAllByCompanyId(UUID companyId);
}
