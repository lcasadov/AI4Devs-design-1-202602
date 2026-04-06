package com.recruitflow.usuarios.domain.port.out;

import com.recruitflow.usuarios.domain.model.Usuario;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Output port (driven port) for usuario persistence.
 */
public interface UsuarioRepository {

  /**
   * Persists a new or updated usuario.
   *
   * @param usuario the domain entity to persist
   * @return the persisted entity
   */
  Usuario save(Usuario usuario);

  /**
   * Finds a usuario by tenant and id.
   *
   * @param companyId the tenant identifier
   * @param id        the user's unique identifier
   * @return an Optional containing the usuario if found
   */
  Optional<Usuario> findByCompanyIdAndId(UUID companyId, UUID id);

  /**
   * Returns all usuarios for a tenant.
   *
   * @param companyId the tenant identifier
   * @return list of usuarios
   */
  List<Usuario> findAllByCompanyId(UUID companyId);
}
