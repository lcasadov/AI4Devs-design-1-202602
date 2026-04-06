package com.recruitflow.vacantes.domain.port.in;

import com.recruitflow.vacantes.application.dto.VacanteRequestDto;
import com.recruitflow.vacantes.application.dto.VacanteResponseDto;
import java.util.List;
import java.util.UUID;

/**
 * Input port (driving port) for the vacantes module.
 *
 * <p>Defines the use cases that the REST adapter invokes. Implementations live in
 * {@code vacantes.domain.service.VacanteService}.</p>
 */
public interface VacanteUseCase {

  /**
   * Creates a new position (vacante).
   *
   * @param companyId the tenant identifier
   * @param request   the creation request DTO
   * @return the created position as a response DTO
   */
  VacanteResponseDto crear(UUID companyId, VacanteRequestDto request);

  /**
   * Retrieves a single position by its identifier.
   *
   * @param companyId the tenant identifier (for isolation)
   * @param id        the position's unique identifier
   * @return the position as a response DTO
   */
  VacanteResponseDto obtenerPorId(UUID companyId, UUID id);

  /**
   * Lists all positions belonging to a tenant.
   *
   * @param companyId the tenant identifier
   * @return list of positions as response DTOs
   */
  List<VacanteResponseDto> listar(UUID companyId);

  /**
   * Updates an existing position.
   *
   * @param companyId the tenant identifier
   * @param id        the position's unique identifier
   * @param request   the update request DTO
   * @return the updated position as a response DTO
   */
  VacanteResponseDto actualizar(UUID companyId, UUID id, VacanteRequestDto request);

  /**
   * Deletes (or closes) a position.
   *
   * @param companyId the tenant identifier
   * @param id        the position's unique identifier
   */
  void eliminar(UUID companyId, UUID id);
}
