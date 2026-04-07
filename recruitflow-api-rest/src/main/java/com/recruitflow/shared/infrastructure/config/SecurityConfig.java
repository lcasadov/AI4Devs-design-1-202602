package com.recruitflow.shared.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration — stateless REST API with minimal public surface.
 *
 * <p>Public endpoints (no auth): POST /api/v1/auth/**, GET /actuator/health.
 * All other endpoints require authentication.</p>
 *
 * <p>IMPORTANT: This configuration must be replaced before any production deployment.
 * See {@code docs/security/security-design.md} for the target security model.</p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Configures the security filter chain for the scaffold phase.
   *
   * <p>CSRF is disabled because the API is stateless (no session cookies).
   * Sessions are never created — each request must carry credentials.</p>
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
        // No sessions — each request is fully self-contained
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // Disable form login and HTTP Basic — JWT filter will handle auth (RF-2)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        // TODO RF-2: replace with JWT filter when auth module is implemented
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/v1/auth/**").permitAll()
            .requestMatchers("/actuator/health").permitAll()
            .anyRequest().authenticated());

    return http.build();
  }
}
