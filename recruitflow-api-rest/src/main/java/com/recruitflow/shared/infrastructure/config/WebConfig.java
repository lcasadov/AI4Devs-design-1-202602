package com.recruitflow.shared.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC web configuration.
 *
 * <p>Configures CORS for local development. In production, CORS origins should be
 * restricted to the known frontend domain via environment variables.
 * See {@code docs/security/security-design.md} for the production CORS policy.</p>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  /**
   * Configures CORS mappings.
   *
   * <p>During development all origins are allowed to facilitate local testing.
   * The allowed origins should be overridden via {@code CORS_ALLOWED_ORIGINS}
   * environment variable before any production deployment.</p>
   *
   * @param registry the CORS registry to configure
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
        // TODO(RF-security): replace with specific allowed origins from env var in production
        .allowedOriginPatterns("*")
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(false)
        .maxAge(3600);
  }
}
