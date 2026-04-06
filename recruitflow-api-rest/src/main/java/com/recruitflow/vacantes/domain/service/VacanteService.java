package com.recruitflow.vacantes.domain.service;

import com.recruitflow.vacantes.application.dto.VacanteRequestDto;
import com.recruitflow.vacantes.application.dto.VacanteResponseDto;
import com.recruitflow.vacantes.domain.port.in.VacanteUseCase;
import com.recruitflow.vacantes.domain.port.out.VacanteRepository;
import java.util.List;
import java.util.UUID;
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
    throw new UnsupportedOperationException("TODO: implement crear vacante");
  }

  /** {@inheritDoc} */
  @Override
  public VacanteResponseDto obtenerPorId(UUID companyId, UUID id) {
    throw new UnsupportedOperationException("TODO: implement obtenerPorId vacante");
  }

  /** {@inheritDoc} */
  @Override
  public List<VacanteResponseDto> listar(UUID companyId) {
    throw new UnsupportedOperationException("TODO: implement listar vacantes");
  }

  /** {@inheritDoc} */
  @Override
  public VacanteResponseDto actualizar(UUID companyId, UUID id, VacanteRequestDto request) {
    throw new UnsupportedOperationException("TODO: implement actualizar vacante");
  }

  /** {@inheritDoc} */
  @Override
  public void eliminar(UUID companyId, UUID id) {
    throw new UnsupportedOperationException("TODO: implement eliminar vacante");
  }
}
