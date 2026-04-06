package com.recruitflow.pipeline.domain.service;

import com.recruitflow.pipeline.application.dto.AplicacionRequestDto;
import com.recruitflow.pipeline.application.dto.AplicacionResponseDto;
import com.recruitflow.pipeline.application.dto.CambioEtapaRequestDto;
import com.recruitflow.pipeline.domain.port.in.PipelineUseCase;
import com.recruitflow.pipeline.domain.port.out.AplicacionRepository;
import java.util.List;
import java.util.UUID;
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
    throw new UnsupportedOperationException("TODO: implement crear aplicacion");
  }

  /** {@inheritDoc} */
  @Override
  public AplicacionResponseDto obtenerPorId(UUID companyId, UUID id) {
    throw new UnsupportedOperationException("TODO: implement obtenerPorId aplicacion");
  }

  /** {@inheritDoc} */
  @Override
  public List<AplicacionResponseDto> listarPorPosicion(UUID companyId, UUID positionId) {
    throw new UnsupportedOperationException("TODO: implement listarPorPosicion");
  }

  /** {@inheritDoc} */
  @Override
  public AplicacionResponseDto cambiarEtapa(UUID companyId, UUID id, CambioEtapaRequestDto request) {
    throw new UnsupportedOperationException("TODO: implement cambiarEtapa — log to StageHistory");
  }
}
