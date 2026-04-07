package com.recruitflow.usuarios.domain.service;

import com.recruitflow.usuarios.application.dto.UsuarioRequestDto;
import com.recruitflow.usuarios.application.dto.UsuarioResponseDto;
import com.recruitflow.usuarios.domain.model.Usuario;
import com.recruitflow.usuarios.domain.port.in.UsuarioUseCase;
import com.recruitflow.usuarios.domain.port.out.UsuarioRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
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
    Usuario usuario = toEntity(request, companyId);
    Usuario saved = usuarioRepository.save(usuario);
    return toDto(saved);
  }

  /** {@inheritDoc} */
  @Override
  public UsuarioResponseDto obtenerPorId(UUID companyId, UUID id) {
    Usuario usuario = usuarioRepository.findByCompanyIdAndId(companyId, id)
        .orElseThrow(() -> new NoSuchElementException("Usuario not found: " + id));
    return toDto(usuario);
  }

  /** {@inheritDoc} */
  @Override
  public List<UsuarioResponseDto> listar(UUID companyId) {
    return usuarioRepository.findAllByCompanyId(companyId).stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  /** {@inheritDoc} */
  @Override
  public UsuarioResponseDto actualizar(UUID companyId, UUID id, UsuarioRequestDto request) {
    Usuario existing = usuarioRepository.findByCompanyIdAndId(companyId, id)
        .orElseThrow(() -> new NoSuchElementException("Usuario not found: " + id));
    applyUpdate(existing, request);
    Usuario saved = usuarioRepository.save(existing);
    return toDto(saved);
  }

  /** {@inheritDoc} */
  @Override
  public void desactivar(UUID companyId, UUID id) {
    Usuario existing = usuarioRepository.findByCompanyIdAndId(companyId, id)
        .orElseThrow(() -> new NoSuchElementException("Usuario not found: " + id));
    existing.setActive(false);
    usuarioRepository.save(existing);
  }

  // -------------------------------------------------------------------------
  // Private helpers — mapping between domain entity and DTO
  // -------------------------------------------------------------------------

  private Usuario toEntity(UsuarioRequestDto request, UUID companyId) {
    Usuario u = new Usuario();
    u.setCompanyId(companyId);
    u.setEmail(request.email());
    u.setFullName(request.fullName());
    u.setRole(request.role());
    u.setActive(true);
    return u;
  }

  private void applyUpdate(Usuario existing, UsuarioRequestDto request) {
    existing.setEmail(request.email());
    existing.setFullName(request.fullName());
    existing.setRole(request.role());
  }

  private UsuarioResponseDto toDto(Usuario u) {
    return new UsuarioResponseDto(
        u.getId(),
        u.getCompanyId(),
        u.getEmail(),
        u.getFullName(),
        u.getRole(),
        u.getAvatarUrl(),
        u.isActive(),
        u.getLastLoginAt()
    );
  }
}
