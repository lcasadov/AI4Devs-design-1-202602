package com.recruitflow.shared.domain.exception;

import java.util.List;

/**
 * Thrown when domain-level validation rules are violated.
 *
 * <p>Carries an optional list of field-level detail messages.
 * Maps to HTTP 400 Bad Request via {@code GlobalExceptionHandler}.</p>
 */
public class ValidationException extends DomainException {

  private static final String DEFAULT_CODE = "VALIDATION_ERROR";

  /** Optional list of per-field or per-rule violation messages. */
  private final List<String> details;

  /**
   * Creates a validation exception with a single message.
   *
   * @param message human-readable description of the violation
   */
  public ValidationException(String message) {
    super(DEFAULT_CODE, message);
    this.details = List.of();
  }

  /**
   * Creates a validation exception with a message and a list of detail messages.
   *
   * @param message human-readable summary
   * @param details list of individual violation messages
   */
  public ValidationException(String message, List<String> details) {
    super(DEFAULT_CODE, message);
    this.details = List.copyOf(details);
  }

  /**
   * Returns the list of validation detail messages.
   *
   * @return immutable list of details, empty if none were provided
   */
  public List<String> getDetails() {
    return details;
  }
}
