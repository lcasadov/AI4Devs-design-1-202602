package com.recruitflow.usuarios.infrastructure.persistence;

import com.recruitflow.usuarios.domain.model.Usuario;
import com.recruitflow.usuarios.domain.port.out.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Infrastructure adapter implementing the {@link UsuarioRepository} output port.
 */
@Component
public class UsuarioRepositoryAdapter implements UsuarioRepository {

  private final UsuarioJpaRepository jpaRepository;

  /**
   * Constructs the adapter with the JPA repository dependency.
   *
   * @param jpaRepository Spring Data JPA repository
   */
  public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  /** {@inheritDoc} */
  @Override
  public Usuario save(Usuario usuario) {
    UsuarioJpaEntity entity = toJpa(usuario);
    UsuarioJpaEntity saved = jpaRepository.save(entity);
    return toDomain(saved);
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Usuario> findByCompanyIdAndId(UUID companyId, UUID id) {
    return jpaRepository.findByCompanyIdAndId(companyId, id)
        .map(this::toDomain);
  }

  /** {@inheritDoc} */
  @Override
  public List<Usuario> findAllByCompanyId(UUID companyId) {
    return jpaRepository.findAllByCompanyId(companyId).stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  // -------------------------------------------------------------------------
  // Private mapping helpers
  // -------------------------------------------------------------------------

  private UsuarioJpaEntity toJpa(Usuario u) {
    UsuarioJpaEntity e = new UsuarioJpaEntity();
    e.setId(u.getId());
    e.setCompanyId(u.getCompanyId());
    e.setEmail(u.getEmail());
    e.setFullName(u.getFullName());
    e.setRole(u.getRole());
    e.setAvatarUrl(u.getAvatarUrl());
    e.setActive(u.isActive());
    e.setLastLoginAt(u.getLastLoginAt());
    return e;
  }

  private Usuario toDomain(UsuarioJpaEntity e) {
    Usuario u = new Usuario();
    u.setId(e.getId());
    u.setCompanyId(e.getCompanyId());
    u.setEmail(e.getEmail());
    u.setFullName(e.getFullName());
    u.setRole(e.getRole());
    u.setAvatarUrl(e.getAvatarUrl());
    u.setActive(e.isActive());
    u.setLastLoginAt(e.getLastLoginAt());
    return u;
  }
}
