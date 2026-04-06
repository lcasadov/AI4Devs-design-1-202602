package com.recruitflow.pipeline.infrastructure.web;

import com.recruitflow.pipeline.application.dto.AplicacionRequestDto;
import com.recruitflow.pipeline.application.dto.AplicacionResponseDto;
import com.recruitflow.pipeline.application.dto.CambioEtapaRequestDto;
import com.recruitflow.pipeline.domain.port.in.PipelineUseCase;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the pipeline module (candidaturas / applications).
 *
 * <p>Placeholder — full business logic is delegated to {@link PipelineUseCase}.</p>
 */
@Tag(name = "Pipeline", description = "Manage candidate applications through the recruitment pipeline")
@RestController
@RequestMapping("/api/v1/pipeline")
public class PipelineController {

  private final PipelineUseCase pipelineUseCase;

  /**
   * Constructs the controller with the required use case dependency.
   *
   * @param pipelineUseCase input port for pipeline operations
   */
  public PipelineController(PipelineUseCase pipelineUseCase) {
    this.pipelineUseCase = pipelineUseCase;
  }

  /**
   * Creates a new application (candidatura).
   *
   * @param companyId tenant identifier
   * @param request   creation request body
   * @return 201 Created with the created application
   */
  @Operation(
      summary = "Create an application",
      description = "Links a candidate to a position and adds them to the pipeline.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Application created",
          content = @Content(schema = @Schema(implementation = AplicacionResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "409", description = "Application already exists for this candidate/position",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping
  public ResponseEntity<AplicacionResponseDto> crear(
      @Parameter(description = "Tenant company UUID", required = true)
      @RequestHeader("X-Company-Id") UUID companyId,
      @Valid @RequestBody AplicacionRequestDto request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(pipelineUseCase.crear(companyId, request));
  }

  /**
   * Lists applications for a given position.
   *
   * @param companyId  tenant identifier
   * @param positionId position's unique identifier
   * @return 200 OK with the list of applications
   */
  @Operation(
      summary = "List applications for a position",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Application list returned"),
      @ApiResponse(responseCode = "401", description = "Not authenticated")
  })
  @GetMapping
  public ResponseEntity<List<AplicacionResponseDto>> listar(
      @Parameter(description = "Tenant company UUID", required = true)
      @RequestHeader("X-Company-Id") UUID companyId,
      @Parameter(description = "Position UUID to filter by", required = true)
      @RequestParam UUID positionId) {
    return ResponseEntity.ok(pipelineUseCase.listarPorPosicion(companyId, positionId));
  }

  /**
   * Returns an application by id.
   *
   * @param companyId tenant identifier
   * @param id        application's unique identifier
   * @return 200 OK or 404 if not found
   */
  @Operation(summary = "Get an application by id", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Application found",
          content = @Content(schema = @Schema(implementation = AplicacionResponseDto.class))),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "404", description = "Application not found",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<AplicacionResponseDto> obtenerPorId(
      @Parameter(description = "Tenant company UUID", required = true)
      @RequestHeader("X-Company-Id") UUID companyId,
      @Parameter(description = "Application UUID", required = true)
      @PathVariable UUID id) {
    return ResponseEntity.ok(pipelineUseCase.obtenerPorId(companyId, id));
  }

  /**
   * Advances or changes the pipeline stage of an application.
   *
   * @param companyId tenant identifier
   * @param id        application's unique identifier
   * @param request   stage change request body
   * @return 200 OK with the updated application
   */
  @Operation(
      summary = "Change pipeline stage",
      description = "Advances or changes the pipeline stage. "
          + "If setting stage to 'discarded', a discardReason is required.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Stage updated",
          content = @Content(schema = @Schema(implementation = AplicacionResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid stage transition",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "404", description = "Application not found",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PatchMapping("/{id}/etapa")
  public ResponseEntity<AplicacionResponseDto> cambiarEtapa(
      @Parameter(description = "Tenant company UUID", required = true)
      @RequestHeader("X-Company-Id") UUID companyId,
      @Parameter(description = "Application UUID", required = true)
      @PathVariable UUID id,
      @Valid @RequestBody CambioEtapaRequestDto request) {
    return ResponseEntity.ok(pipelineUseCase.cambiarEtapa(companyId, id, request));
  }
}
