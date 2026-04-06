package com.recruitflow.pipeline.domain.port.in;

import com.recruitflow.pipeline.application.dto.AplicacionRequestDto;
import com.recruitflow.pipeline.application.dto.AplicacionResponseDto;
import com.recruitflow.pipeline.application.dto.CambioEtapaRequestDto;
import java.util.List;
import java.util.UUID;

/**
 * Input port (driving port) for the pipeline module.
 *
 * <p>Manages candidate applications through the recruitment pipeline stages.</p>
 */
public interface PipelineUseCase {

  /**
   * Creates a new application (candidatura) linking a candidate to a position.
   *
   * @param companyId the tenant identifier
   * @param request   the creation request DTO
   * @return the created application as a response DTO
   */
  AplicacionResponseDto crear(UUID companyId, AplicacionRequestDto request);

  /**
   * Retrieves an application by id.
   *
   * @param companyId the tenant identifier
   * @param id        the application's unique identifier
   * @return the application as a response DTO
   */
  AplicacionResponseDto obtenerPorId(UUID companyId, UUID id);

  /**
   * Lists all applications for a given position.
   *
   * @param companyId  the tenant identifier
   * @param positionId the position's unique identifier
   * @return list of application response DTOs
   */
  List<AplicacionResponseDto> listarPorPosicion(UUID companyId, UUID positionId);

  /**
   * Advances or changes the pipeline stage of an application.
   *
   * @param companyId the tenant identifier
   * @param id        the application's unique identifier
   * @param request   the stage change request (new stage + optional notes/discard reason)
   * @return the updated application as a response DTO
   */
  AplicacionResponseDto cambiarEtapa(UUID companyId, UUID id, CambioEtapaRequestDto request);
}
