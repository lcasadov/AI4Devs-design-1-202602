package com.recruitflow.shared.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration — permissive bootstrap mode.
 *
 * <p>All requests are permitted without authentication during the initial scaffold phase.
 * Full JWT-based security (RS256) will be implemented in a dedicated security story
 * once the endpoint contracts are established.</p>
 *
 * <p>IMPORTANT: This configuration must be replaced before any production deployment.
 * See {@code docs/security/security-design.md} for the target security model.</p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Configures a permissive security filter chain for the scaffold phase.
   *
   * <p>CSRF is disabled because the API is stateless (no session cookies).
   * All endpoints are open — this will be tightened in subsequent iterations.</p>
   *
   * @param http the {@link HttpSecurity} builder provided by Spring Security
   * @return configured {@link SecurityFilterChain}
   * @throws Exception if the security configuration fails
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // Stateless REST API — CSRF not applicable
        .csrf(AbstractHttpConfigurer::disable)
        // TODO(RF-security): replace with JWT bearer token validation (RS256)
        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

    return http.build();
  }
}
