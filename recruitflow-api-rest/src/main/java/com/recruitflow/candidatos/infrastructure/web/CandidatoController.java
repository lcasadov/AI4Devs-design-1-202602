package com.recruitflow.candidatos.infrastructure.web;

import com.recruitflow.candidatos.application.dto.CandidatoRequestDto;
import com.recruitflow.candidatos.application.dto.CandidatoResponseDto;
import com.recruitflow.candidatos.domain.port.in.CandidatoUseCase;
import com.recruitflow.shared.infrastructure.web.ErrorResponse;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the candidatos module.
 *
 * <p>Full business logic is delegated to {@link CandidatoUseCase}.</p>
 */
@Tag(name = "Candidatos", description = "Manage candidates in the talent pool")
@RestController
@RequestMapping("/api/v1/candidatos")
public class CandidatoController {

  private final CandidatoUseCase candidatoUseCase;

  /**
   * Constructs the controller with the required use case dependency.
   *
   * @param candidatoUseCase input port for candidato operations
   */
  public CandidatoController(CandidatoUseCase candidatoUseCase) {
    this.candidatoUseCase = candidatoUseCase;
  }

  /**
   * Resolves the tenant company identifier from the authenticated principal.
   *
   * <p>TODO RF-2: extract companyId from JWT claims once the auth module is implemented:
   * {@code return ((JwtPrincipal) authentication.getPrincipal()).getCompanyId();}</p>
   *
   * @return a placeholder company UUID until JWT auth is implemented
   */
  private UUID resolveCompanyId() {
    // TODO RF-2: extract from JWT claims
    // BLOCKER: hardcoded placeholder — production deployment blocked until RF-2
    String activeProfile = System.getProperty("spring.profiles.active", "dev");
    if (!activeProfile.contains("dev") && !activeProfile.contains("test")) {
      throw new IllegalStateException("companyId resolution not implemented. Deploy blocked until RF-2.");
    }
    return UUID.fromString("00000000-0000-0000-0000-000000000001");
  }

  /**
   * Creates a new candidate.
   *
   * @param request creation request body
   * @return 201 Created with the created candidate
   */
  @Operation(summary = "Create a candidate", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Candidate created",
          content = @Content(schema = @Schema(implementation = CandidatoResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "409", description = "Candidate with this email already exists",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping
  public ResponseEntity<CandidatoResponseDto> crear(
      @Valid @RequestBody CandidatoRequestDto request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(candidatoUseCase.crear(resolveCompanyId(), request));
  }

  /**
   * Lists all candidates for the authenticated tenant.
   *
   * @return 200 OK with candidate list
   */
  @Operation(summary = "List candidates", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Candidate list returned"),
      @ApiResponse(responseCode = "401", description = "Not authenticated")
  })
  @GetMapping
  public ResponseEntity<List<CandidatoResponseDto>> listar() {
    return ResponseEntity.ok(candidatoUseCase.listar(resolveCompanyId()));
  }

  /**
   * Returns a candidate by id.
   *
   * @param id candidate's unique identifier
   * @return 200 OK or 404 if not found
   */
  @Operation(summary = "Get a candidate by id", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Candidate found",
          content = @Content(schema = @Schema(implementation = CandidatoResponseDto.class))),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "404", description = "Candidate not found",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<CandidatoResponseDto> obtenerPorId(
      @Parameter(description = "Candidate UUID", required = true)
      @PathVariable UUID id) {
    return ResponseEntity.ok(candidatoUseCase.obtenerPorId(resolveCompanyId(), id));
  }

  /**
   * Updates a candidate's profile.
   *
   * @param id      candidate's unique identifier
   * @param request update request body
   * @return 200 OK with updated candidate
   */
  @Operation(summary = "Update a candidate", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Candidate updated",
          content = @Content(schema = @Schema(implementation = CandidatoResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "404", description = "Candidate not found",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping("/{id}")
  public ResponseEntity<CandidatoResponseDto> actualizar(
      @Parameter(description = "Candidate UUID", required = true)
      @PathVariable UUID id,
      @Valid @RequestBody CandidatoRequestDto request) {
    return ResponseEntity.ok(candidatoUseCase.actualizar(resolveCompanyId(), id, request));
  }

  /**
   * Anonymizes a candidate's personal data (GDPR right to be forgotten).
   *
   * @param id candidate's unique identifier
   * @return 204 No Content on success
   */
  @Operation(summary = "Anonymize candidate personal data (GDPR)",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Candidate data anonymized"),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "404", description = "Candidate not found",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  // GDPR: only authenticated users (HR role in RF-2)
  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{id}/datos-personales")
  public ResponseEntity<Void> anonimizarDatosPersonales(
      @Parameter(description = "Candidate UUID", required = true)
      @PathVariable UUID id) {
    candidatoUseCase.anonimizar(resolveCompanyId(), id);
    return ResponseEntity.noContent().build();
  }
}
