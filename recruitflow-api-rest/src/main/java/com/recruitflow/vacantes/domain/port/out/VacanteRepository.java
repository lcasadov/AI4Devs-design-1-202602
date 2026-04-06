package com.recruitflow.vacantes.domain.port.out;

import com.recruitflow.vacantes.domain.model.Vacante;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Output port (driven port) for vacante persistence.
 *
 * <p>This interface belongs to the domain layer and defines what the domain
 * needs from persistence — not how it is stored. The JPA adapter implementing
 * this interface lives in {@code vacantes.infrastructure.persistence.VacanteRepositoryAdapter}.</p>
 */
public interface VacanteRepository {

  /**
   * Persists a new or updated vacante.
   *
   * @param vacante the domain entity to persist
   * @return the persisted entity (may include generated fields such as id or code)
   */
  Vacante save(Vacante vacante);

  /**
   * Finds a vacante by its id scoped to a specific tenant.
   *
   * @param companyId the tenant identifier
   * @param id        the position's unique identifier
   * @return an {@link Optional} containing the vacante if found, or empty if not
   */
  Optional<Vacante> findByCompanyIdAndId(UUID companyId, UUID id);

  /**
   * Returns all vacantes belonging to a tenant.
   *
   * @param companyId the tenant identifier
   * @return list of vacantes, may be empty
   */
  List<Vacante> findAllByCompanyId(UUID companyId);

  /**
   * Deletes a vacante by its id.
   *
   * @param id the position's unique identifier
   */
  void deleteById(UUID id);
}
