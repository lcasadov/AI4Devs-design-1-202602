package com.recruitflow.vacantes.infrastructure.persistence;

import com.recruitflow.vacantes.domain.model.Vacante;
import com.recruitflow.vacantes.domain.port.out.VacanteRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
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
    VacanteJpaEntity entity = toJpa(vacante);
    VacanteJpaEntity saved = jpaRepository.save(entity);
    return toDomain(saved);
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Vacante> findByCompanyIdAndId(UUID companyId, UUID id) {
    return jpaRepository.findByCompanyIdAndId(companyId, id)
        .map(this::toDomain);
  }

  /** {@inheritDoc} */
  @Override
  public List<Vacante> findAllByCompanyId(UUID companyId) {
    return jpaRepository.findAllByCompanyId(companyId).stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  /** {@inheritDoc} */
  @Override
  public void deleteById(UUID id) {
    jpaRepository.deleteById(id);
  }

  // -------------------------------------------------------------------------
  // Private mapping helpers
  // -------------------------------------------------------------------------

  private VacanteJpaEntity toJpa(Vacante v) {
    VacanteJpaEntity e = new VacanteJpaEntity();
    e.setId(v.getId());
    e.setCode(v.getCode());
    e.setCompanyId(v.getCompanyId());
    e.setClientId(v.getClientId());
    e.setRecruiterId(v.getRecruiterId());
    e.setTitle(v.getTitle());
    e.setDescription(v.getDescription());
    e.setLocation(v.getLocation());
    e.setModality(v.getModality());
    e.setSalaryMin(v.getSalaryMin());
    e.setSalaryMax(v.getSalaryMax());
    e.setStatus(v.getStatus());
    e.setPriority(v.getPriority());
    e.setDeadline(v.getDeadline());
    e.setOpenedAt(v.getOpenedAt());
    e.setClosedAt(v.getClosedAt());
    return e;
  }

  private Vacante toDomain(VacanteJpaEntity e) {
    Vacante v = new Vacante();
    v.setId(e.getId());
    v.setCode(e.getCode());
    v.setCompanyId(e.getCompanyId());
    v.setClientId(e.getClientId());
    v.setRecruiterId(e.getRecruiterId());
    v.setTitle(e.getTitle());
    v.setDescription(e.getDescription());
    v.setLocation(e.getLocation());
    v.setModality(e.getModality());
    v.setSalaryMin(e.getSalaryMin());
    v.setSalaryMax(e.getSalaryMax());
    v.setStatus(e.getStatus());
    v.setPriority(e.getPriority());
    v.setDeadline(e.getDeadline());
    v.setOpenedAt(e.getOpenedAt());
    v.setClosedAt(e.getClosedAt());
    return v;
  }
}
