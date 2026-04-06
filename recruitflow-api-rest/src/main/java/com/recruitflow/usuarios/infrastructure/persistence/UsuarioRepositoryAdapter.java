package com.recruitflow.usuarios.infrastructure.persistence;

import com.recruitflow.usuarios.domain.model.Usuario;
import com.recruitflow.usuarios.domain.port.out.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    throw new UnsupportedOperationException("TODO: implement save usuario");
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Usuario> findByCompanyIdAndId(UUID companyId, UUID id) {
    throw new UnsupportedOperationException("TODO: implement findByCompanyIdAndId usuario");
  }

  /** {@inheritDoc} */
  @Override
  public List<Usuario> findAllByCompanyId(UUID companyId) {
    throw new UnsupportedOperationException("TODO: implement findAllByCompanyId usuario");
  }
}
