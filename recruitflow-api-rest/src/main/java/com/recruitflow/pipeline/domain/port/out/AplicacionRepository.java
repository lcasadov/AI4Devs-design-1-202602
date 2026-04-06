package com.recruitflow.pipeline.domain.port.out;

import com.recruitflow.pipeline.domain.model.Aplicacion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Output port (driven port) for application (candidatura) persistence.
 */
public interface AplicacionRepository {

  /**
   * Persists a new or updated application.
   *
   * @param aplicacion the domain entity to persist
   * @return the persisted entity
   */
  Aplicacion save(Aplicacion aplicacion);

  /**
   * Finds an application by its id.
   *
   * @param id the application's unique identifier
   * @return an Optional containing the application if found
   */
  Optional<Aplicacion> findById(UUID id);

  /**
   * Returns all applications for a given position.
   *
   * @param positionId the position's unique identifier
   * @return list of applications
   */
  List<Aplicacion> findAllByPositionId(UUID positionId);
}
