package com.recruitflow.pipeline.infrastructure.persistence;

import com.recruitflow.pipeline.domain.model.Aplicacion;
import com.recruitflow.pipeline.domain.port.out.AplicacionRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
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
    AplicacionJpaEntity entity = toJpa(aplicacion);
    AplicacionJpaEntity saved = jpaRepository.save(entity);
    return toDomain(saved);
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Aplicacion> findById(UUID id) {
    return jpaRepository.findById(id).map(this::toDomain);
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Aplicacion> findByIdAndCompanyId(UUID id, UUID companyId) {
    return jpaRepository.findByIdAndCompanyId(id, companyId).map(this::toDomain);
  }

  /** {@inheritDoc} */
  @Override
  public List<Aplicacion> findAllByPositionId(UUID positionId) {
    return jpaRepository.findAllByPositionId(positionId).stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  // -------------------------------------------------------------------------
  // Private mapping helpers
  // -------------------------------------------------------------------------

  private AplicacionJpaEntity toJpa(Aplicacion a) {
    AplicacionJpaEntity e = new AplicacionJpaEntity();
    e.setId(a.getId());
    e.setCompanyId(a.getCompanyId());
    e.setPositionId(a.getPositionId());
    e.setCandidateId(a.getCandidateId());
    e.setRecruiterId(a.getRecruiterId());
    e.setStage(a.getStage());
    e.setMatchScore(a.getMatchScore());
    e.setMatchDetail(a.getMatchDetail());
    e.setDiscardReason(a.getDiscardReason());
    e.setSource(a.getSource());
    e.setCreatedAt(a.getCreatedAt());
    e.setUpdatedAt(a.getUpdatedAt());
    return e;
  }

  private Aplicacion toDomain(AplicacionJpaEntity e) {
    Aplicacion a = new Aplicacion();
    a.setId(e.getId());
    a.setCompanyId(e.getCompanyId());
    a.setPositionId(e.getPositionId());
    a.setCandidateId(e.getCandidateId());
    a.setRecruiterId(e.getRecruiterId());
    a.setStage(e.getStage());
    a.setMatchScore(e.getMatchScore());
    a.setMatchDetail(e.getMatchDetail());
    a.setDiscardReason(e.getDiscardReason());
    a.setSource(e.getSource());
    a.setCreatedAt(e.getCreatedAt());
    a.setUpdatedAt(e.getUpdatedAt());
    return a;
  }
}
