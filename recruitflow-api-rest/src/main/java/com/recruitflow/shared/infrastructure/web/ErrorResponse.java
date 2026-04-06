package com.recruitflow.shared.infrastructure.web;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

/**
 * Standard API error response envelope.
 *
 * <p>All error responses returned by the API use this structure so that clients
 * have a consistent contract for error handling regardless of the error origin.</p>
 *
 * @param code      machine-readable error code (e.g. {@code "NOT_FOUND"}, {@code "VALIDATION_ERROR"})
 * @param message   human-readable summary of the error
 * @param details   list of additional details (e.g. per-field validation messages); empty if none
 * @param timestamp ISO-8601 timestamp of when the error occurred
 */
@Schema(description = "Standard API error response")
public record ErrorResponse(

    @Schema(description = "Machine-readable error code", example = "VALIDATION_ERROR")
    String code,

    @Schema(description = "Human-readable error message")
    String message,

    @Schema(description = "List of additional error details (field violations, causes, etc.)")
    List<String> details,

    @Schema(description = "Timestamp of the error in ISO-8601 format")
    Instant timestamp
) {

  /**
   * Factory method for a simple error response with no details.
   *
   * @param code    machine-readable error code
   * @param message human-readable message
   * @return a new {@code ErrorResponse} with an empty details list and current timestamp
   */
  public static ErrorResponse of(String code, String message) {
    return new ErrorResponse(code, message, List.of(), Instant.now());
  }

  /**
   * Factory method for an error response with detail messages.
   *
   * @param code    machine-readable error code
   * @param message human-readable summary
   * @param details list of violation or cause messages
   * @return a new {@code ErrorResponse} with the provided details and current timestamp
   */
  public static ErrorResponse of(String code, String message, List<String> details) {
    return new ErrorResponse(code, message, List.copyOf(details), Instant.now());
  }
}
