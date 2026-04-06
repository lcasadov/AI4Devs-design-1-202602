package com.recruitflow.shared.domain.exception;

/**
 * Thrown when a requested domain entity cannot be found.
 *
 * <p>Maps to HTTP 404 Not Found via {@code GlobalExceptionHandler}.</p>
 */
public class NotFoundException extends DomainException {

  private static final String DEFAULT_CODE = "NOT_FOUND";

  /**
   * Creates a not-found exception for a specific entity and identifier.
   *
   * @param entityName simple name of the entity (e.g. {@code "Position"})
   * @param id         identifier that was looked up
   */
  public NotFoundException(String entityName, Object id) {
    super(DEFAULT_CODE, entityName + " not found with id: " + id);
  }

  /**
   * Creates a not-found exception with a custom message.
   *
   * @param message human-readable description of what was not found
   */
  public NotFoundException(String message) {
    super(DEFAULT_CODE, message);
  }
}
