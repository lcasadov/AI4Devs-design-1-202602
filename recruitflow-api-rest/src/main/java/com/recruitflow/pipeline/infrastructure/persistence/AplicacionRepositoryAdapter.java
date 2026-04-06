package com.recruitflow.pipeline.infrastructure.persistence;

import com.recruitflow.pipeline.domain.model.Aplicacion;
import com.recruitflow.pipeline.domain.port.out.AplicacionRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * Infrastructure adapter implementing the {@link AplicacionRepository} output port.
 */
@Component
public class AplicacionRepositoryAdapter implements AplicacionRepository {

  private final AplicacionJpaRepository jpaRepository;

  /**
   * Constructs the adapter with the JPA repository dependency.
   *
   * @param jpaRepository Spring Data JPA repository
   */
  public AplicacionRepositoryAdapter(AplicacionJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  /** {@inheritDoc} */
  @Override
  public Aplicacion save(Aplicacion aplicacion) {
    throw new UnsupportedOperationException("TODO: implement save aplicacion");
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Aplicacion> findById(UUID id) {
    throw new UnsupportedOperationException("TODO: implement findById aplicacion");
  }

  /** {@inheritDoc} */
  @Override
  public List<Aplicacion> findAllByPositionId(UUID positionId) {
    throw new UnsupportedOperationException("TODO: implement findAllByPositionId");
  }
}
