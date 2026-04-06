package com.recruitflow.candidatos.domain.port.in;

import com.recruitflow.candidatos.application.dto.CandidatoRequestDto;
import com.recruitflow.candidatos.application.dto.CandidatoResponseDto;
import java.util.List;
import java.util.UUID;

/**
 * Input port (driving port) for the candidatos module.
 */
public interface CandidatoUseCase {

  /**
   * Creates a new candidate in the talent pool.
   *
   * @param companyId the tenant identifier
   * @param request   the creation request DTO
   * @return the created candidate as a response DTO
   */
  CandidatoResponseDto crear(UUID companyId, CandidatoRequestDto request);

  /**
   * Retrieves a single candidate by id.
   *
   * @param companyId the tenant identifier
   * @param id        the candidate's unique identifier
   * @return the candidate as a response DTO
   */
  CandidatoResponseDto obtenerPorId(UUID companyId, UUID id);

  /**
   * Lists all candidates for a tenant.
   *
   * @param companyId the tenant identifier
   * @return list of candidate response DTOs
   */
  List<CandidatoResponseDto> listar(UUID companyId);

  /**
   * Updates a candidate's profile.
   *
   * @param companyId the tenant identifier
   * @param id        the candidate's unique identifier
   * @param request   the update request DTO
   * @return the updated candidate as a response DTO
   */
  CandidatoResponseDto actualizar(UUID companyId, UUID id, CandidatoRequestDto request);

  /**
   * Anonymizes a candidate's PII fields (GDPR right to be forgotten).
   *
   * @param companyId the tenant identifier
   * @param id        the candidate's unique identifier
   */
  void anonimizar(UUID companyId, UUID id);
}
