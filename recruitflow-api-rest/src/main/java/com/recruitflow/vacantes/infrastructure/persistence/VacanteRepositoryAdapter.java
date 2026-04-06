package com.recruitflow.vacantes.infrastructure.persistence;

import com.recruitflow.vacantes.domain.model.Vacante;
import com.recruitflow.vacantes.domain.port.out.VacanteRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * Infrastructure adapter implementing the {@link VacanteRepository} output port.
 *
 * <p>Bridges the domain port and the Spring Data JPA repository. Responsible for
 * converting between {@link VacanteJpaEntity} (infrastructure) and {@link Vacante} (domain).
 * No business logic belongs here.</p>
 */
@Component
public class VacanteRepositoryAdapter implements VacanteRepository {

  private final VacanteJpaRepository jpaRepository;

  /**
   * Constructs the adapter with its required JPA repository dependency.
   *
   * @param jpaRepository Spring Data JPA repository
   */
  public VacanteRepositoryAdapter(VacanteJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  /** {@inheritDoc} */
  @Override
  public Vacante save(Vacante vacante) {
    throw new UnsupportedOperationException("TODO: implement save — map Vacante to JPA entity and back");
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Vacante> findByCompanyIdAndId(UUID companyId, UUID id) {
    throw new UnsupportedOperationException("TODO: implement findByCompanyIdAndId — map JPA entity to domain");
  }

  /** {@inheritDoc} */
  @Override
  public List<Vacante> findAllByCompanyId(UUID companyId) {
    throw new UnsupportedOperationException("TODO: implement findAllByCompanyId — map JPA entities to domain");
  }

  /** {@inheritDoc} */
  @Override
  public void deleteById(UUID id) {
    throw new UnsupportedOperationException("TODO: implement deleteById");
  }
}
