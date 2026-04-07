package com.recruitflow.vacantes.domain.service;

import com.recruitflow.vacantes.application.dto.VacanteRequestDto;
import com.recruitflow.vacantes.application.dto.VacanteResponseDto;
import com.recruitflow.vacantes.domain.model.Vacante;
import com.recruitflow.vacantes.domain.port.in.VacanteUseCase;
import com.recruitflow.vacantes.domain.port.out.VacanteRepository;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Domain service implementing the {@link VacanteUseCase} input port.
 *
 * <p>All business logic for the vacantes module lives here.
 * This class must not import any Spring MVC, JPA, or persistence classes — it depends
 * only on the {@link VacanteRepository} output port and domain models/DTOs.</p>
 */
@Service
public class VacanteService implements VacanteUseCase {

  private final VacanteRepository vacanteRepository;

  /**
   * Constructs a {@code VacanteService} with its required repository dependency.
   *
   * @param vacanteRepository output port for vacante persistence
   */
  public VacanteService(VacanteRepository vacanteRepository) {
    this.vacanteRepository = vacanteRepository;
  }

  /** {@inheritDoc} */
  @Override
  public VacanteResponseDto crear(UUID companyId, VacanteRequestDto request) {
    Vacante vacante = toEntity(request, companyId);
    Vacante saved = vacanteRepository.save(vacante);
    return toDto(saved);
  }

  /** {@inheritDoc} */
  @Override
  public VacanteResponseDto obtenerPorId(UUID companyId, UUID id) {
    Vacante vacante = vacanteRepository.findByCompanyIdAndId(companyId, id)
        .orElseThrow(() -> new NoSuchElementException("Vacante not found: " + id));
    return toDto(vacante);
  }

  /** {@inheritDoc} */
  @Override
  public List<VacanteResponseDto> listar(UUID companyId) {
    return vacanteRepository.findAllByCompanyId(companyId).stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  /** {@inheritDoc} */
  @Override
  public VacanteResponseDto actualizar(UUID companyId, UUID id, VacanteRequestDto request) {
    Vacante existing = vacanteRepository.findByCompanyIdAndId(companyId, id)
        .orElseThrow(() -> new NoSuchElementException("Vacante not found: " + id));
    applyUpdate(existing, request);
    Vacante saved = vacanteRepository.save(existing);
    return toDto(saved);
  }

  /** {@inheritDoc} */
  @Override
  public void eliminar(UUID companyId, UUID id) {
    vacanteRepository.findByCompanyIdAndId(companyId, id)
        .orElseThrow(() -> new NoSuchElementException("Vacante not found: " + id));
    vacanteRepository.deleteById(id);
  }

  // -------------------------------------------------------------------------
  // Private helpers — mapping between domain entity and DTO
  // -------------------------------------------------------------------------

  private Vacante toEntity(VacanteRequestDto request, UUID companyId) {
    Vacante v = new Vacante();
    v.setCompanyId(companyId);
    v.setClientId(request.clientId());
    v.setRecruiterId(request.recruiterId());
    v.setTitle(request.title());
    v.setDescription(request.description());
    v.setLocation(request.location());
    v.setModality(request.modality());
    v.setSalaryMin(request.salaryMin());
    v.setSalaryMax(request.salaryMax());
    v.setStatus("draft");
    v.setPriority(request.priority());
    v.setDeadline(request.deadline());
    v.setOpenedAt(Instant.now());
    return v;
  }

  private void applyUpdate(Vacante existing, VacanteRequestDto request) {
    existing.setClientId(request.clientId());
    existing.setRecruiterId(request.recruiterId());
    existing.setTitle(request.title());
    existing.setDescription(request.description());
    existing.setLocation(request.location());
    existing.setModality(request.modality());
    existing.setSalaryMin(request.salaryMin());
    existing.setSalaryMax(request.salaryMax());
    existing.setPriority(request.priority());
    existing.setDeadline(request.deadline());
  }

  private VacanteResponseDto toDto(Vacante v) {
    return new VacanteResponseDto(
        v.getId(),
        v.getCode(),
        v.getCompanyId(),
        v.getClientId(),
        v.getRecruiterId(),
        v.getTitle(),
        v.getDescription(),
        v.getLocation(),
        v.getModality(),
        v.getSalaryMin(),
        v.getSalaryMax(),
        v.getStatus(),
        v.getPriority(),
        v.getDeadline(),
        v.getOpenedAt(),
        v.getClosedAt()
    );
  }
}
