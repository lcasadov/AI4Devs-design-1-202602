package com.recruitflow.shared.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI / Swagger UI configuration for the RecruitFlow REST API.
 *
 * <p>Defines the JWT bearer security scheme used by all secured endpoints.
 * The generated spec is served at {@code /v3/api-docs} and the UI at
 * {@code /swagger-ui.html}.</p>
 */
@Configuration
public class OpenApiConfig {

  /**
   * Produces the {@link OpenAPI} bean consumed by springdoc to build the spec.
   *
   * @return configured OpenAPI instance with project metadata and JWT security scheme
   */
  @Bean
  public OpenAPI recruitFlowOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("RecruitFlow REST API")
            .description(
                "ATS (Applicant Tracking System) for recruitment agencies. "
                + "Manages the full recruitment lifecycle: vacancies, candidates, pipeline, and matching.")
            .version("1.0.0")
            .contact(new Contact()
                .name("RecruitFlow Team")
                .email("admin@recruitflow.io")))
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .components(new Components()
            .addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("JWT RS256 — obtain token via POST /api/v1/auth/login")));
  }
}
