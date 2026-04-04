# Prompt 03 — Arquitectura del sistema

**Documento generado:** `docs/architecture/project.md`

---

## Prompt utilizado

```
Eres un arquitecto de software senior experto en arquitectura hexagonal, DDD y sistemas distribuidos.

Genera el documento de arquitectura completo para RecruitFlow con las siguientes secciones:

1. **Variables de configuración del proyecto** (tabla para consumo por agentes de IA):
   - REPO_ROOT, BASE_BRANCH, JIRA_PROJECT_KEY, PROJECT_NAME, BACKEND_DIR, FRONTEND_DIR

2. **Visión general** — descripción del sistema, objetivos de arquitectura, restricciones

3. **Modelo C4**:
   - Nivel 1: Diagrama de contexto del sistema (Mermaid)
   - Nivel 2: Diagrama de contenedores (frontend, backend, BD, servicios externos)
   - Nivel 3: Diagrama de componentes por módulo principal

4. **Arquitectura hexagonal** por módulo:
   - Capas: Domain / Application / Infrastructure
   - Estructura de paquetes Java
   - Estructura de carpetas React/TypeScript

5. **Modelo de datos de alto nivel** — entidades principales y relaciones

6. **Arquitectura de despliegue**:
   - Docker Compose para desarrollo local
   - Diagrama de infraestructura (nginx, backend, SQL Server)
   - Variables de entorno

7. **Flujos de datos principales**:
   - Flujo de creación de vacante y matching
   - Flujo de pipeline de candidato
   - Diagrama de secuencia para autenticación JWT

8. **ADRs (Architecture Decision Records)**:
   - ADR-001: Arquitectura hexagonal vs. MVC en capas
   - ADR-002: SQL Server vs. PostgreSQL
   - ADR-003: Multi-tenancy por company_id vs. schema-per-tenant
   - ADR-004: JWT RS256 vs. HS256
   - ADR-005: React + Vite vs. Next.js

9. **Contratos API por módulo** — para cada uno de los 9 módulos:
   - Endpoints principales (método + path)
   - Request body con tipos y validaciones
   - Response schema
   - HTTP status codes
   - Ejemplos JSON

Stack: Spring Boot 3.3, Hibernate/JPA, AOP, React 18, TypeScript, Vite, SQL Server 2022, Docker.
Arquitectura multi-tenant con company_id como discriminador de tenant.
```

---

*Sesión: AI4Devs Design — RecruitFlow*
