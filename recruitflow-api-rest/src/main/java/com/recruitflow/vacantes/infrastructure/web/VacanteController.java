package com.recruitflow.vacantes.infrastructure.web;

import com.recruitflow.shared.infrastructure.web.ErrorResponse;
import com.recruitflow.vacantes.application.dto.VacanteRequestDto;
import com.recruitflow.vacantes.application.dto.VacanteResponseDto;
import com.recruitflow.vacantes.domain.port.in.VacanteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the vacantes (positions) module.
 *
 * <p>All method bodies are placeholder stubs — business logic is delegated to
 * {@link VacanteUseCase}. Full implementation will be added in subsequent stories.</p>
 */
@Tag(name = "Vacantes", description = "Manage open positions (vacantes / positions)")
@RestController
@RequestMapping("/api/v1/vacantes")
public class VacanteController {

  private final VacanteUseCase vacanteUseCase;

  /**
   * Constructs the controller with the required use case dependency.
   *
   * @param vacanteUseCase input port for vacante operations
   */
  public VacanteController(VacanteUseCase vacanteUseCase) {
    this.vacanteUseCase = vacanteUseCase;
  }

  /**
   * Creates a new position for the authenticated company.
   *
   * @param companyId the tenant identifier from the request header
   * @param request   the creation request body
   * @return 201 Created with the created position
   */
  @Operation(
      summary = "Create a position",
      description = "Creates a new open position for the authenticated tenant.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Position created",
          content = @Content(schema = @Schema(implementation = VacanteResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request data",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "403", description = "Insufficient permissions")
  })
  @PostMapping
  public ResponseEntity<VacanteResponseDto> crear(
      @Parameter(description = "Tenant company UUID", required = true)
      @RequestHeader("X-Company-Id") UUID companyId,
      @Valid @RequestBody VacanteRequestDto request) {
    VacanteResponseDto created = vacanteUseCase.crear(companyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  /**
   * Lists all positions for the authenticated company.
   *
   * @param companyId the tenant identifier from the request header
   * @return 200 OK with the list of positions
   */
  @Operation(
      summary = "List positions",
      description = "Returns all positions belonging to the authenticated tenant.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Position list returned"),
      @ApiResponse(responseCode = "401", description = "Not authenticated")
  })
  @GetMapping
  public ResponseEntity<List<VacanteResponseDto>> listar(
      @Parameter(description = "Tenant company UUID", required = true)
      @RequestHeader("X-Company-Id") UUID companyId) {
    return ResponseEntity.ok(vacanteUseCase.listar(companyId));
  }

  /**
   * Returns a single position by id.
   *
   * @param companyId the tenant identifier
   * @param id        the position's unique identifier
   * @return 200 OK with the position, or 404 if not found
   */
  @Operation(
      summary = "Get a position by id",
      description = "Returns the position identified by the given UUID, scoped to the tenant.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Position found",
          content = @Content(schema = @Schema(implementation = VacanteResponseDto.class))),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "404", description = "Position not found",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<VacanteResponseDto> obtenerPorId(
      @Parameter(description = "Tenant company UUID", required = true)
      @RequestHeader("X-Company-Id") UUID companyId,
      @Parameter(description = "Position UUID", required = true)
      @PathVariable UUID id) {
    return ResponseEntity.ok(vacanteUseCase.obtenerPorId(companyId, id));
  }

  /**
   * Updates an existing position.
   *
   * @param companyId the tenant identifier
   * @param id        the position's unique identifier
   * @param request   the update request body
   * @return 200 OK with the updated position
   */
  @Operation(
      summary = "Update a position",
      description = "Updates the position identified by the given UUID.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Position updated",
          content = @Content(schema = @Schema(implementation = VacanteResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request data",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
      @ApiResponse(responseCode = "404", description = "Position not found",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping("/{id}")
  public ResponseEntity<VacanteResponseDto> actualizar(
      @Parameter(description = "Tenant company UUID", required = true)
      @RequestHeader("X-Company-Id") UUID companyId,
      @Parameter(description = "Position UUID", required = true)
      @PathVariable UUID id,
      @Valid @RequestBody VacanteRequestDto request) {
    return ResponseEntity.ok(vacanteUseCase.actualizar(companyId, id, request));
  }

  /**
   * Deletes a position.
   *
   * @param companyId the tenant identifier
   * @param id        the position's unique identifier
   * @return 204 No Content
   */
  @Operation(
      summary = "Delete a position",
      description = "Deletes (or closes) the position identified by the given UUID.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Position deleted"),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
      @ApiResponse(responseCode = "404", description = "Position not found",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(
      @Parameter(description = "Tenant company UUID", required = true)
      @RequestHeader("X-Company-Id") UUID companyId,
      @Parameter(description = "Position UUID", required = true)
      @PathVariable UUID id) {
    vacanteUseCase.eliminar(companyId, id);
    return ResponseEntity.noContent().build();
  }
}
