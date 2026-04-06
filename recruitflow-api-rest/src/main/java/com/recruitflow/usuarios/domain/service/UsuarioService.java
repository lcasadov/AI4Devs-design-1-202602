package com.recruitflow.usuarios.domain.service;

import com.recruitflow.usuarios.application.dto.UsuarioRequestDto;
import com.recruitflow.usuarios.application.dto.UsuarioResponseDto;
import com.recruitflow.usuarios.domain.port.in.UsuarioUseCase;
import com.recruitflow.usuarios.domain.port.out.UsuarioRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Domain service implementing the {@link UsuarioUseCase} input port.
 */
@Service
public class UsuarioService implements UsuarioUseCase {

  private final UsuarioRepository usuarioRepository;

  /**
   * Constructs the service with its required repository dependency.
   *
   * @param usuarioRepository output port for usuario persistence
   */
  public UsuarioService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  /** {@inheritDoc} */
  @Override
  public UsuarioResponseDto crear(UUID companyId, UsuarioRequestDto request) {
    throw new UnsupportedOperationException("TODO: implement crear usuario");
  }

  /** {@inheritDoc} */
  @Override
  public UsuarioResponseDto obtenerPorId(UUID companyId, UUID id) {
    throw new UnsupportedOperationException("TODO: implement obtenerPorId usuario");
  }

  /** {@inheritDoc} */
  @Override
  public List<UsuarioResponseDto> listar(UUID companyId) {
    throw new UnsupportedOperationException("TODO: implement listar usuarios");
  }

  /** {@inheritDoc} */
  @Override
  public UsuarioResponseDto actualizar(UUID companyId, UUID id, UsuarioRequestDto request) {
    throw new UnsupportedOperationException("TODO: implement actualizar usuario");
  }

  /** {@inheritDoc} */
  @Override
  public void desactivar(UUID companyId, UUID id) {
    throw new UnsupportedOperationException("TODO: implement desactivar usuario");
  }
}
