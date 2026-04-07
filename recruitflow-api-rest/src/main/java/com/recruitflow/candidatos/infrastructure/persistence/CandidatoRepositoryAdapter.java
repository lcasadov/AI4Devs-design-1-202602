package com.recruitflow.candidatos.infrastructure.persistence;

import com.recruitflow.candidatos.domain.model.Candidato;
import com.recruitflow.candidatos.domain.port.out.CandidatoRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
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
    CandidatoJpaEntity entity = toJpa(candidato);
    CandidatoJpaEntity saved = jpaRepository.save(entity);
    return toDomain(saved);
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Candidato> findByCompanyIdAndId(UUID companyId, UUID id) {
    return jpaRepository.findByCompanyIdAndId(companyId, id)
        .map(this::toDomain);
  }

  /** {@inheritDoc} */
  @Override
  public List<Candidato> findAllByCompanyId(UUID companyId) {
    return jpaRepository.findAllByCompanyId(companyId).stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  // -------------------------------------------------------------------------
  // Private mapping helpers
  // -------------------------------------------------------------------------

  private CandidatoJpaEntity toJpa(Candidato c) {
    CandidatoJpaEntity e = new CandidatoJpaEntity();
    e.setId(c.getId());
    e.setCompanyId(c.getCompanyId());
    e.setEmail(c.getEmail());
    e.setPhone(c.getPhone());
    e.setFullName(c.getFullName());
    e.setLocation(c.getLocation());
    e.setYearsExperience(c.getYearsExperience());
    e.setAvailability(c.getAvailability());
    e.setSalaryExpectation(c.getSalaryExpectation());
    e.setCvUrl(c.getCvUrl());
    e.setLinkedinUrl(c.getLinkedinUrl());
    e.setSource(c.getSource());
    e.setGdprConsent(c.isGdprConsent());
    e.setGdprConsentAt(c.getGdprConsentAt());
    e.setAnonymizedAt(c.getAnonymizedAt());
    e.setCreatedAt(c.getCreatedAt());
    return e;
  }

  private Candidato toDomain(CandidatoJpaEntity e) {
    Candidato c = new Candidato();
    c.setId(e.getId());
    c.setCompanyId(e.getCompanyId());
    c.setEmail(e.getEmail());
    c.setPhone(e.getPhone());
    c.setFullName(e.getFullName());
    c.setLocation(e.getLocation());
    c.setYearsExperience(e.getYearsExperience());
    c.setAvailability(e.getAvailability());
    c.setSalaryExpectation(e.getSalaryExpectation());
    c.setCvUrl(e.getCvUrl());
    c.setLinkedinUrl(e.getLinkedinUrl());
    c.setSource(e.getSource());
    c.setGdprConsent(e.isGdprConsent());
    c.setGdprConsentAt(e.getGdprConsentAt());
    c.setAnonymizedAt(e.getAnonymizedAt());
    c.setCreatedAt(e.getCreatedAt());
    return c;
  }
}
