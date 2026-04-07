package com.recruitflow.shared.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC web configuration.
 *
 * <p>Configures CORS for local development. In production, CORS origins should be
 * restricted to the known frontend domain via the {@code cors.allowed-origins} property.
 * See {@code docs/security/security-design.md} for the production CORS policy.</p>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${cors.allowed-origins:http://localhost:5173}")
  private String allowedOrigins;

  /**
   * Configures CORS mappings using the {@code cors.allowed-origins} property.
   *
   * <p>Defaults to {@code http://localhost:5173} (Vite dev server) when the property
   * is not set. Override via application.yml or environment variable for production.</p>
   *
   * @param registry the CORS registry to configure
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
        .allowedOrigins(allowedOrigins)
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(false)
        .maxAge(3600);
  }
}
