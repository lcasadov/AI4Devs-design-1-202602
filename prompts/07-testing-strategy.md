# Prompt 07 — Estrategia de Testing

**Documento generado:** `docs/quality/testing-strategy.md`

---

## Prompt utilizado

```
Eres experto Tester y tienes que generar el documento de Testing Strategy para RecruitFlow.

El stack es:
- Backend: Java 21, Spring Boot 3.3, JUnit 5, Mockito, Testcontainers, JaCoCo
- Frontend: React 18, TypeScript, Vitest, React Testing Library, MSW (Mock Service Worker)
- E2E: Cypress 13
- CI/CD: GitHub Actions + Azure Pipelines
- Base de datos de tests: SQL Server 2022 con Testcontainers

Genera el documento completo con:

1. **Pirámide de testing** con distribución 80/15/5:
   - 80% Tests unitarios (rápidos, sin dependencias externas)
   - 15% Tests de integración (con BD real via Testcontainers, MSW para frontend)
   - 5% Tests E2E (Cypress, flujos críticos de negocio)
   - Tiempos de ejecución esperados por capa
   - Cuándo ejecutar cada capa (pre-commit, pre-push, CI, nightly)

2. **Tests unitarios Backend** (JUnit 5 + Mockito):
   - Patrón Object Mother para fixtures
   - Tests de casos de uso (Application layer)
   - Tests de dominio (validaciones, reglas de negocio)
   - Tests de RBAC con @ParameterizedTest (matriz de permisos)
   - Tests de AOP (auditoría)
   - Ejemplos de código para cada tipo

3. **Tests de integración Backend** (Testcontainers + SQL Server):
   - @DataJpaTest con contenedor SQL Server
   - @WebMvcTest con @WithMockUser
   - Tests de repositorios JPA con filtros de tenant
   - Tests de API REST completos (MockMvc)
   - Ejemplos de código

4. **Tests unitarios Frontend** (Vitest + RTL + MSW):
   - Factories con Faker para datos de prueba
   - Tests de componentes con renderHook
   - Tests de stores Zustand con act()
   - Handlers MSW para simular respuestas API
   - Tests de formularios y validaciones
   - Ejemplos de código

5. **Tests E2E** (Cypress 13):
   - 4 flujos críticos:
     * Crear vacante → matching → aplicar candidato
     * Pipeline Kanban: mover candidato entre etapas
     * Autenticación + control de acceso por rol (ADMIN vs RECRUITER)
     * Onboarding de candidato completo
   - Fixtures y custom commands
   - Ejemplos de código

6. **Estructura de directorios** de tests:
   - Backend: árbol completo de src/test/java/
   - Frontend: árbol completo de src/__tests__/ y cypress/

7. **Pipeline CI/CD**:
   - 4 etapas: lint → unit → integration → e2e
   - GitHub Actions workflow YAML completo
   - Quality gates: cobertura mínima 80% líneas / 75% ramas
   - Artefactos: reportes JaCoCo, reportes Jest, vídeos Cypress

8. **Convenciones de nomenclatura** de tests y organización de suites

Incluye ejemplos de código reales y completos para cada tipo de test.
```

---

*Sesión: AI4Devs Design — RecruitFlow*
