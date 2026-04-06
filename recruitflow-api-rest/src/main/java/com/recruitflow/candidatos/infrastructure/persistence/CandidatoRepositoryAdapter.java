package com.recruitflow.candidatos.infrastructure.persistence;

import com.recruitflow.candidatos.domain.model.Candidato;
import com.recruitflow.candidatos.domain.port.out.CandidatoRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * Infrastructure adapter implementing the {@link CandidatoRepository} output port.
 *
 * <p>Translates between {@link CandidatoJpaEntity} (infrastructure) and
 * {@link Candidato} (domain). No business logic here.</p>
 */
@Component
public class CandidatoRepositoryAdapter implements CandidatoRepository {

  private final CandidatoJpaRepository jpaRepository;

  /**
   * Constructs the adapter with the JPA repository dependency.
   *
   * @param jpaRepository Spring Data JPA repository
   */
  public CandidatoRepositoryAdapter(CandidatoJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  /** {@inheritDoc} */
  @Override
  public Candidato save(Candidato candidato) {
    throw new UnsupportedOperationException("TODO: implement save — map Candidato to JPA entity and back");
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Candidato> findByCompanyIdAndId(UUID companyId, UUID id) {
    throw new UnsupportedOperationException("TODO: implement findByCompanyIdAndId");
  }

  /** {@inheritDoc} */
  @Override
  public List<Candidato> findAllByCompanyId(UUID companyId) {
    throw new UnsupportedOperationException("TODO: implement findAllByCompanyId");
  }
}
