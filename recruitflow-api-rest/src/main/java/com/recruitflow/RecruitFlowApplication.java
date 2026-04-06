package com.recruitflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the RecruitFlow ATS REST API.
 *
 * <p>Built with Spring Boot 3.3 using Hexagonal Architecture (Ports and Adapters).
 * Each business module (vacantes, candidatos, usuarios, pipeline) is self-contained
 * with its own domain, application, and infrastructure layers.</p>
 */
@SpringBootApplication
public class RecruitFlowApplication {

  /**
   * Application entry point.
   *
   * @param args command-line arguments passed by the JVM
   */
  public static void main(String[] args) {
    SpringApplication.run(RecruitFlowApplication.class, args);
  }
}
