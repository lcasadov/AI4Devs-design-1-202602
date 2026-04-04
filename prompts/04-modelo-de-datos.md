# Prompt 04 — Modelo de datos

**Documentos generados:** `docs/architecture/data-model.md` · `docs/architecture/data-model-additional.md`

---

## Prompt principal

```
Eres un experto en diseño de bases de datos relacionales y modelado de datos para sistemas SaaS.

Genera el modelo de datos completo para RecruitFlow con:

1. **Diagrama entidad-relación** en formato Mermaid (erDiagram) con las 16 entidades base:
   - companies (tenant raíz)
   - users (admin / recruiter)
   - positions (vacantes)
   - candidates
   - applications (postulaciones — relación position ↔ candidate)
   - pipeline_stages (etapas del pipeline por vacante)
   - interviews
   - interview_feedback
   - technical_tests
   - test_results
   - job_offers (propuestas)
   - communications (email / WhatsApp)
   - communication_templates
   - documents (CVs, adjuntos)
   - tags
   - audit_log

2. Para cada entidad:
   - Nombre de tabla (snake_case)
   - Columnas con tipo SQL Server, nullable, PK/FK/UQ
   - Índices recomendados
   - Descripción de propósito

3. **Convenciones**:
   - Todas las tablas con company_id (multi-tenancy)
   - created_at / updated_at / deleted_at en todas las entidades
   - Soft delete con deleted_at
   - UUIDs como PK (UNIQUEIDENTIFIER en SQL Server)

4. **Relaciones** con cardinalidades explicadas (1:N, N:M con tabla intermedia)

Base de datos: SQL Server 2022. ORM: Hibernate/JPA con @Filter para multi-tenancy.
```

---

## Prompt de continuación (entidades adicionales)

```
Añade al modelo de datos las 15 entidades adicionales para funcionalidades avanzadas:

- matching_scores (resultados del algoritmo de matching CV–vacante)
- candidate_skills / position_required_skills (habilidades requeridas vs. disponibles)
- skill_catalog (catálogo maestro de skills)
- position_publications (publicaciones en portales externos: LinkedIn, InfoJobs)
- calendar_events (integración con Google Calendar / Outlook)
- notifications (notificaciones in-app y push)
- webhook_subscriptions (integraciones externas)
- api_keys (autenticación machine-to-machine)
- refresh_tokens (gestión de sesiones JWT)
- rate_limit_log (auditoría de rate limiting)
- email_tracking (seguimiento de aperturas y clics)
- whatsapp_messages (mensajes de WhatsApp con estado de entrega)
- report_snapshots (informes generados y cacheados)
- feature_flags (configuración de funcionalidades por tenant)
- billing_events (registro de eventos de facturación)

Mismo formato que las entidades base: erDiagram Mermaid + tabla de columnas + índices.
```

---

*Sesión: AI4Devs Design — RecruitFlow*
