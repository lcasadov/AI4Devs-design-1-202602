package com.recruitflow.shared.domain.exception;

/**
 * Base exception for all domain-level errors in RecruitFlow.
 *
 * <p>All business rule violations should extend this class so that the
 * {@code GlobalExceptionHandler} can map them to appropriate HTTP responses.</p>
 */
public class DomainException extends RuntimeException {

  /** Machine-readable error code for the API {@code ErrorResponse}. */
  private final String code;

  /**
   * Creates a domain exception with a code and a human-readable message.
   *
   * @param code    machine-readable error code (e.g. {@code "POSITION_CLOSED"})
   * @param message human-readable description of the error
   */
  public DomainException(String code, String message) {
    super(message);
    this.code = code;
  }

  /**
   * Creates a domain exception with a code, message and a root cause.
   *
   * @param code    machine-readable error code
   * @param message human-readable description
   * @param cause   original exception that triggered this domain error
   */
  public DomainException(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  /**
   * Returns the machine-readable error code.
   *
   * @return error code string
   */
  public String getCode() {
    return code;
  }
}
