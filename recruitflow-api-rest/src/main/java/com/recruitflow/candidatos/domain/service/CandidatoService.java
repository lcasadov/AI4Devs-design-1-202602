package com.recruitflow.candidatos.domain.service;

import com.recruitflow.candidatos.application.dto.CandidatoRequestDto;
import com.recruitflow.candidatos.application.dto.CandidatoResponseDto;
import com.recruitflow.candidatos.domain.port.in.CandidatoUseCase;
import com.recruitflow.candidatos.domain.port.out.CandidatoRepository;
import java.util.List;
import java.util.UUID;
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
    throw new UnsupportedOperationException("TODO: implement crear candidato");
  }

  /** {@inheritDoc} */
  @Override
  public CandidatoResponseDto obtenerPorId(UUID companyId, UUID id) {
    throw new UnsupportedOperationException("TODO: implement obtenerPorId candidato");
  }

  /** {@inheritDoc} */
  @Override
  public List<CandidatoResponseDto> listar(UUID companyId) {
    throw new UnsupportedOperationException("TODO: implement listar candidatos");
  }

  /** {@inheritDoc} */
  @Override
  public CandidatoResponseDto actualizar(UUID companyId, UUID id, CandidatoRequestDto request) {
    throw new UnsupportedOperationException("TODO: implement actualizar candidato");
  }

  /** {@inheritDoc} */
  @Override
  public void anonimizar(UUID companyId, UUID id) {
    throw new UnsupportedOperationException("TODO: implement anonimizar candidato (GDPR)");
  }
}
