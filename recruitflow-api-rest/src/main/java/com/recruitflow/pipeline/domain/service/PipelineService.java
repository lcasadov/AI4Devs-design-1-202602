package com.recruitflow.pipeline.domain.service;

import com.recruitflow.pipeline.application.dto.AplicacionRequestDto;
import com.recruitflow.pipeline.application.dto.AplicacionResponseDto;
import com.recruitflow.pipeline.application.dto.CambioEtapaRequestDto;
import com.recruitflow.pipeline.domain.model.Aplicacion;
import com.recruitflow.pipeline.domain.port.in.PipelineUseCase;
import com.recruitflow.pipeline.domain.port.out.AplicacionRepository;
import com.recruitflow.shared.domain.exception.NotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Domain service implementing the {@link PipelineUseCase} input port.
 *
 * <p>Owns all pipeline business logic. No infrastructure dependencies directly — only
 * the {@link AplicacionRepository} output port is injected.</p>
 */
@Service
public class PipelineService implements PipelineUseCase {

  private final AplicacionRepository aplicacionRepository;

  /**
   * Constructs the service with its required repository dependency.
   *
   * @param aplicacionRepository output port for application persistence
   */
  public PipelineService(AplicacionRepository aplicacionRepository) {
    this.aplicacionRepository = aplicacionRepository;
  }

  /** {@inheritDoc} */
  @Override
  public AplicacionResponseDto crear(UUID companyId, AplicacionRequestDto request) {
    Aplicacion aplicacion = toEntity(request, companyId);
    Aplicacion saved = aplicacionRepository.save(aplicacion);
    return toDto(saved);
  }

  /** {@inheritDoc} */
  @Override
  public AplicacionResponseDto obtenerPorId(UUID companyId, UUID id) {
    Aplicacion aplicacion = aplicacionRepository.findByIdAndCompanyId(id, companyId)
        .orElseThrow(() -> new NotFoundException("Aplicacion", id.toString()));
    return toDto(aplicacion);
  }

  /** {@inheritDoc} */
  @Override
  public List<AplicacionResponseDto> listarPorPosicion(UUID companyId, UUID positionId) {
    return aplicacionRepository.findAllByPositionId(positionId).stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  /** {@inheritDoc} */
  @Override
  public AplicacionResponseDto cambiarEtapa(UUID companyId, UUID id, CambioEtapaRequestDto request) {
    Aplicacion existing = aplicacionRepository.findByIdAndCompanyId(id, companyId)
        .orElseThrow(() -> new NotFoundException("Aplicacion", id.toString()));
    existing.setStage(request.stage());
    existing.setDiscardReason(request.discardReason());
    existing.setUpdatedAt(Instant.now());
    Aplicacion saved = aplicacionRepository.save(existing);
    return toDto(saved);
  }

  // -------------------------------------------------------------------------
  // Private helpers — mapping between domain entity and DTO
  // -------------------------------------------------------------------------

  private Aplicacion toEntity(AplicacionRequestDto request, UUID companyId) {
    Aplicacion a = new Aplicacion();
    a.setCompanyId(companyId);
    a.setPositionId(request.positionId());
    a.setCandidateId(request.candidateId());
    a.setRecruiterId(request.recruiterId());
    a.setSource(request.source());
    a.setStage("shortlisted");
    a.setCreatedAt(Instant.now());
    a.setUpdatedAt(Instant.now());
    return a;
  }

  private AplicacionResponseDto toDto(Aplicacion a) {
    return new AplicacionResponseDto(
        a.getId(),
        a.getPositionId(),
        a.getCandidateId(),
        a.getRecruiterId(),
        a.getStage(),
        a.getMatchScore(),
        a.getDiscardReason(),
        a.getSource(),
        a.getCreatedAt(),
        a.getUpdatedAt()
    );
  }
}
