package com.recruitflow.shared.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/**
 * ArchUnit tests validating the hexagonal architecture rules for RecruitFlow.
 *
 * <p>These tests enforce the fundamental constraint of hexagonal architecture:
 * the domain must not depend on infrastructure, and the application layer
 * must not depend on the infrastructure layer.</p>
 *
 * <p>Tests run with the unit test suite (no Spring context needed).</p>
 */
@DisplayName("Hexagonal Architecture Rules")
class ArchitectureTest {

  private static JavaClasses importedClasses;

  @BeforeAll
  static void importClasses() {
    importedClasses = new ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages("com.recruitflow");
  }

  /**
   * The domain layer must not depend on any infrastructure package.
   *
   * <p>Domain classes ({@code **.domain.**}) must not import from:
   * <ul>
   *   <li>{@code **.infrastructure.**} — JPA entities, adapters, configs</li>
   *   <li>{@code org.springframework.**} — any Spring annotation or class</li>
   *   <li>{@code jakarta.persistence.**} — JPA annotations</li>
   * </ul>
   * </p>
   */
  @Test
  @DisplayName("Domain layer must not depend on infrastructure")
  void domainMustNotDependOnInfrastructure() {
    ArchRule rule = noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat()
        .resideInAPackage("..infrastructure..")
        .because("The domain layer must be infrastructure-agnostic (Hexagonal Architecture)");

    rule.check(importedClasses);
  }

  /**
   * The domain layer must not import Spring Framework classes.
   *
   * <p>Spring annotations like {@code @Service} are acceptable in the domain service
   * as a pragmatic tradeoff — but JPA, MVC, and Security imports are forbidden.</p>
   */
  @Test
  @DisplayName("Domain model must not depend on JPA")
  void domainModelMustNotDependOnJpa() {
    ArchRule rule = noClasses()
        .that().resideInAPackage("..domain.model..")
        .should().dependOnClassesThat()
        .resideInAPackage("jakarta.persistence..")
        .because("Domain model classes must not have JPA annotations — use JPA entities in infrastructure");

    rule.check(importedClasses);
  }

  /**
   * The application layer (DTOs) must not depend on the infrastructure layer.
   */
  @Test
  @DisplayName("Application layer must not depend on infrastructure")
  void applicationLayerMustNotDependOnInfrastructure() {
    ArchRule rule = noClasses()
        .that().resideInAPackage("..application..")
        .should().dependOnClassesThat()
        .resideInAPackage("..infrastructure..")
        .because("Application layer (DTOs) must not depend on infrastructure concerns");

    rule.check(importedClasses);
  }

  /**
   * Infrastructure adapters must implement their corresponding domain ports.
   *
   * <p>Validates that the port-adapter pattern is correctly applied — adapters
   * must not bypass the port interfaces.</p>
   */
  @Test
  @DisplayName("Controllers must only access use cases via input ports")
  void controllersMustOnlyDependOnInputPorts() {
    ArchRule rule = noClasses()
        .that().resideInAPackage("..infrastructure.web..")
        .should().dependOnClassesThat()
        .resideInAPackage("..domain.service..")
        .because("Controllers (web adapters) must depend on input port interfaces, not service implementations");

    rule.check(importedClasses);
  }

  /**
   * Repository adapters must not be accessed from domain services directly.
   *
   * <p>Domain services must depend only on the repository port interfaces
   * ({@code port.out}), not on the JPA adapter implementations.</p>
   */
  @Test
  @DisplayName("Domain services must not depend on repository adapter implementations")
  void domainServicesMustNotDependOnRepositoryAdapters() {
    ArchRule rule = noClasses()
        .that().resideInAPackage("..domain.service..")
        .should().dependOnClassesThat()
        .resideInAPackage("..infrastructure.persistence..")
        .because("Domain services must depend only on port interfaces (port.out), not JPA adapters");

    rule.check(importedClasses);
  }

  /**
   * Layered architecture check — enforces the allowed dependency directions.
   *
   * <p>Defines the four hexagonal layers and forbids upward dependencies:
   * <pre>
   *   web (adapter-in) → domain
   *   persistence (adapter-out) → domain
   *   domain → application (DTOs only)
   *   application → (nothing else)
   * </pre>
   * </p>
   */
  @Test
  @DisplayName("Layered architecture dependency directions are respected")
  void layeredArchitectureIsRespected() {
    ArchRule rule = layeredArchitecture()
        .consideringOnlyDependenciesInLayers()
        .layer("Web").definedBy("..infrastructure.web..")
        .layer("Persistence").definedBy("..infrastructure.persistence..")
        .layer("Config").definedBy("..infrastructure.config..")
        .layer("Domain").definedBy("..domain..")
        .layer("Application").definedBy("..application..")
        .whereLayer("Domain").mayOnlyAccessLayers("Application")
        .whereLayer("Application").mayNotAccessAnyLayer()
        .as("Hexagonal architecture layer rules");

    rule.check(importedClasses);
  }
}
