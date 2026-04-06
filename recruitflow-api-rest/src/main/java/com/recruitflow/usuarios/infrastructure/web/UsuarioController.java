package com.recruitflow.usuarios.infrastructure.web;

import com.recruitflow.shared.infrastructure.web.ErrorResponse;
import com.recruitflow.usuarios.application.dto.UsuarioRequestDto;
import com.recruitflow.usuarios.application.dto.UsuarioResponseDto;
import com.recruitflow.usuarios.domain.port.in.UsuarioUseCase;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the usuarios module.
 *
 * <p>Placeholder — full business logic is delegated to {@link UsuarioUseCase}.</p>
 */
@Tag(name = "Usuarios", description = "Manage internal users (recruiters, managers, admins)")
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

  private final UsuarioUseCase usuarioUseCase;

  /**
   * Constructs the controller with the required use case dependency.
   *
   * @param usuarioUseCase input port for usuario operations
   */
  public UsuarioController(UsuarioUseCase usuarioUseCase) {
    this.usuarioUseCase = usuarioUseCase;
  }

  /**
   * Creates a new internal user.
   *
   * @param companyId tenant identifier
   * @param request   creation request body
   * @return 201 Created with the created user
   */
  @Operation(summary = "Create a user", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "User created",
          content = @Content(schema = @Schema(implementation = UsuarioResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
      @ApiResponse(responseCode = "409", description = "User with this email already exists",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping
  public ResponseEntity<UsuarioResponseDto> crear(
      @Parameter(description = "Tenant company UUID", required = true)
      @RequestHeader("X-Company-Id") UUID companyId,
      @Valid @RequestBody UsuarioRequestDto request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioUseCase.crear(companyId, request));
  }

  /**
   * Lists all users for the authenticated tenant.
   *
   * @param companyId tenant identifier
   * @return 200 OK with user list
   */
  @Operation(summary = "List users", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User list returned"),
      @ApiResponse(responseCode = "401", description = "Not authenticated")
  })
  @GetMapping
  public ResponseEntity<List<UsuarioResponseDto>> listar(
      @Parameter(description = "Tenant company UUID", required = true)
      @RequestHeader("X-Company-Id") UUID companyId) {
    return ResponseEntity.ok(usuarioUseCase.listar(companyId));
  }

  /**
   * Returns a user by id.
   *
   * @param companyId tenant identifier
   * @param id        user's unique identifier
   * @return 200 OK or 404 if not found
   */
  @Operation(summary = "Get a user by id", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User found",
          content = @Content(schema = @Schema(implementation = UsuarioResponseDto.class))),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<UsuarioResponseDto> obtenerPorId(
      @Parameter(description = "Tenant company UUID", required = true)
      @RequestHeader("X-Company-Id") UUID companyId,
      @Parameter(description = "User UUID", required = true)
      @PathVariable UUID id) {
    return ResponseEntity.ok(usuarioUseCase.obtenerPorId(companyId, id));
  }

  /**
   * Updates a user's profile.
   *
   * @param companyId tenant identifier
   * @param id        user's unique identifier
   * @param request   update request body
   * @return 200 OK with updated user
   */
  @Operation(summary = "Update a user", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User updated",
          content = @Content(schema = @Schema(implementation = UsuarioResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Not authenticated"),
      @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping("/{id}")
  public ResponseEntity<UsuarioResponseDto> actualizar(
      @Parameter(description = "Tenant company UUID", required = true)
      @RequestHeader("X-Company-Id") UUID companyId,
      @Parameter(description = "User UUID", required = true)
      @PathVariable UUID id,
      @Valid @RequestBody UsuarioRequestDto request) {
    return ResponseEntity.ok(usuarioUseCase.actualizar(companyId, id, request));
  }
}
