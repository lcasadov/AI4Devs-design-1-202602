package com.recruitflow.usuarios.domain.port.in;

import com.recruitflow.usuarios.application.dto.UsuarioRequestDto;
import com.recruitflow.usuarios.application.dto.UsuarioResponseDto;
import java.util.List;
import java.util.UUID;

/**
 * Input port (driving port) for the usuarios module.
 */
public interface UsuarioUseCase {

  /**
   * Creates a new user for a tenant.
   *
   * @param companyId the tenant identifier
   * @param request   the creation request DTO
   * @return the created user as a response DTO
   */
  UsuarioResponseDto crear(UUID companyId, UsuarioRequestDto request);

  /**
   * Retrieves a user by id.
   *
   * @param companyId the tenant identifier
   * @param id        the user's unique identifier
   * @return the user as a response DTO
   */
  UsuarioResponseDto obtenerPorId(UUID companyId, UUID id);

  /**
   * Lists all users for a tenant.
   *
   * @param companyId the tenant identifier
   * @return list of user response DTOs
   */
  List<UsuarioResponseDto> listar(UUID companyId);

  /**
   * Updates a user's profile.
   *
   * @param companyId the tenant identifier
   * @param id        the user's unique identifier
   * @param request   the update request DTO
   * @return the updated user as a response DTO
   */
  UsuarioResponseDto actualizar(UUID companyId, UUID id, UsuarioRequestDto request);

  /**
   * Deactivates a user account.
   *
   * @param companyId the tenant identifier
   * @param id        the user's unique identifier
   */
  void desactivar(UUID companyId, UUID id);
}
