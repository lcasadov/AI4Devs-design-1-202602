package com.recruitflow.shared.infrastructure.web;

import com.recruitflow.shared.domain.exception.DomainException;
import com.recruitflow.shared.domain.exception.NotFoundException;
import com.recruitflow.shared.domain.exception.ValidationException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Centralized exception handler for all REST controllers.
 *
 * <p>Translates exceptions into consistent {@link ErrorResponse} payloads.
 * All error-response construction is done here — never inline in controllers.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handles {@link NotFoundException} → HTTP 404.
   *
   * @param ex the not-found exception
   * @return 404 response with error details
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
    log.warn("Resource not found: {}", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.of(ex.getCode(), ex.getMessage()));
  }

  /**
   * Handles {@link ValidationException} → HTTP 400.
   *
   * @param ex the domain validation exception
   * @return 400 response with validation details
   */
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
    log.warn("Domain validation error: {}", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.of(ex.getCode(), ex.getMessage(), ex.getDetails()));
  }

  /**
   * Handles generic {@link DomainException} → HTTP 422 Unprocessable Entity.
   *
   * <p>Catches all domain exceptions that are not more specifically handled above.</p>
   *
   * @param ex the domain exception
   * @return 422 response with error code and message
   */
  @ExceptionHandler(DomainException.class)
  public ResponseEntity<ErrorResponse> handleDomain(DomainException ex) {
    log.warn("Domain exception [{}]: {}", ex.getCode(), ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(ErrorResponse.of(ex.getCode(), ex.getMessage()));
  }

  /**
   * Handles Spring MVC Bean Validation failures ({@link MethodArgumentNotValidException}) → HTTP 400.
   *
   * @param ex the binding result exception thrown by {@code @Valid}
   * @return 400 response with per-field violation messages
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleBeanValidation(MethodArgumentNotValidException ex) {
    List<String> details = ex.getBindingResult().getFieldErrors().stream()
        .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
        .toList();
    log.warn("Bean validation failed: {}", details);
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.of("VALIDATION_ERROR", "Request validation failed", details));
  }

  /**
   * Catch-all handler for unexpected exceptions → HTTP 500.
   *
   * <p>Stack traces are never included in the response body (OWASP A05).
   * The full exception is logged server-side for diagnosis.</p>
   *
   * @param ex any unhandled exception
   * @return 500 response with a generic message
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
    log.error("Unexpected error", ex);
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.of("INTERNAL_ERROR", "An unexpected error occurred"));
  }
}
