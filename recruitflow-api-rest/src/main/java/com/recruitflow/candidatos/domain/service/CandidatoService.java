package com.recruitflow.candidatos.domain.service;

import com.recruitflow.candidatos.application.dto.CandidatoRequestDto;
import com.recruitflow.candidatos.application.dto.CandidatoResponseDto;
import com.recruitflow.candidatos.domain.model.Candidato;
import com.recruitflow.candidatos.domain.port.in.CandidatoUseCase;
import com.recruitflow.candidatos.domain.port.out.CandidatoRepository;
import com.recruitflow.shared.domain.exception.NotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Domain service implementing the {@link CandidatoUseCase} input port.
 *
 * <p>All business logic for the candidatos module lives here.
 * Dependencies are injected via constructor — no field injection.</p>
 */
@Service
public class CandidatoService implements CandidatoUseCase {

  private final CandidatoRepository candidatoRepository;

  /**
   * Constructs the service with its required repository dependency.
   *
   * @param candidatoRepository output port for candidato persistence
   */
  public CandidatoService(CandidatoRepository candidatoRepository) {
    this.candidatoRepository = candidatoRepository;
  }

  /** {@inheritDoc} */
  @Override
  public CandidatoResponseDto crear(UUID companyId, CandidatoRequestDto request) {
    Candidato candidato = toEntity(request, companyId);
    Candidato saved = candidatoRepository.save(candidato);
    return toDto(saved);
  }

  /** {@inheritDoc} */
  @Override
  public CandidatoResponseDto obtenerPorId(UUID companyId, UUID id) {
    Candidato candidato = candidatoRepository.findByCompanyIdAndId(companyId, id)
        .orElseThrow(() -> new NotFoundException("Candidato", id.toString()));
    return toDto(candidato);
  }

  /** {@inheritDoc} */
  @Override
  public List<CandidatoResponseDto> listar(UUID companyId) {
    return candidatoRepository.findAllByCompanyId(companyId).stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  /** {@inheritDoc} */
  @Override
  public CandidatoResponseDto actualizar(UUID companyId, UUID id, CandidatoRequestDto request) {
    Candidato existing = candidatoRepository.findByCompanyIdAndId(companyId, id)
        .orElseThrow(() -> new NotFoundException("Candidato", id.toString()));
    applyUpdate(existing, request);
    Candidato saved = candidatoRepository.save(existing);
    return toDto(saved);
  }

  /** {@inheritDoc} */
  @Override
  public void anonimizar(UUID companyId, UUID id) {
    Candidato existing = candidatoRepository.findByCompanyIdAndId(companyId, id)
        .orElseThrow(() -> new NotFoundException("Candidato", id.toString()));
    existing.setFullName("ANONYMIZED");
    existing.setEmail("anonymized-" + id + "@redacted.invalid");
    existing.setPhone(null);
    existing.setLocation(null);
    existing.setLinkedinUrl(null);
    existing.setAnonymizedAt(Instant.now());
    candidatoRepository.save(existing);
  }

  // -------------------------------------------------------------------------
  // Private helpers — mapping between domain entity and DTO
  // -------------------------------------------------------------------------

  private Candidato toEntity(CandidatoRequestDto request, UUID companyId) {
    Candidato c = new Candidato();
    c.setCompanyId(companyId);
    c.setEmail(request.email());
    c.setPhone(request.phone());
    c.setFullName(request.fullName());
    c.setLocation(request.location());
    c.setYearsExperience(request.yearsExperience());
    c.setAvailability(request.availability());
    c.setSalaryExpectation(request.salaryExpectation());
    c.setLinkedinUrl(request.linkedinUrl());
    c.setSource(request.source());
    c.setGdprConsent(Boolean.TRUE.equals(request.gdprConsent()));
    c.setGdprConsentAt(Boolean.TRUE.equals(request.gdprConsent()) ? Instant.now() : null);
    c.setCreatedAt(Instant.now());
    return c;
  }

  private void applyUpdate(Candidato existing, CandidatoRequestDto request) {
    existing.setPhone(request.phone());
    existing.setFullName(request.fullName());
    existing.setLocation(request.location());
    existing.setYearsExperience(request.yearsExperience());
    existing.setAvailability(request.availability());
    existing.setSalaryExpectation(request.salaryExpectation());
    existing.setLinkedinUrl(request.linkedinUrl());
  }

  private CandidatoResponseDto toDto(Candidato c) {
    return new CandidatoResponseDto(
        c.getId(),
        c.getCompanyId(),
        c.getEmail(),
        c.getPhone(),
        c.getFullName(),
        c.getLocation(),
        c.getYearsExperience(),
        c.getAvailability(),
        c.getSalaryExpectation(),
        c.getCvUrl(),
        c.getLinkedinUrl(),
        c.getSource(),
        c.isGdprConsent(),
        c.getGdprConsentAt(),
        c.getAnonymizedAt(),
        c.getCreatedAt()
    );
  }
}
