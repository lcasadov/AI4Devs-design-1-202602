# Prompt 01 — Visión del producto y PRD

**Documento generado:** `docs/product/vision.md` · `docs/product/PRD.md`

---

## Prompt utilizado

```
Eres un experto en producto digital y SaaS B2B. Voy a construir un ATS (Applicant Tracking System)
llamado RecruitFlow, orientado a agencias de reclutamiento medianas.

Genera dos documentos:

1. **vision.md** — Visión del producto con:
   - Descripción del problema que resuelve
   - Público objetivo y arquetipos de usuario (Admin, Recruiter)
   - Propuesta de valor diferencial frente a competidores (Workday, Greenhouse, Lever)
   - Modelo de negocio (SaaS multi-tenant, suscripción mensual por agencia)
   - Métricas de éxito (KPIs)
   - Roadmap de alto nivel por fases (MVP → v2 → v3)

2. **PRD.md** — Product Requirements Document con:
   - Resumen ejecutivo
   - Objetivos del producto y criterios de éxito
   - Funcionalidades principales organizadas por módulo:
     * Gestión de vacantes
     * Base de candidatos
     * Pipeline de selección (Kanban)
     * Matching automático CV–vacante
     * Entrevistas y agenda
     * Pruebas técnicas
     * Propuestas y contratos
     * Comunicaciones (email/WhatsApp)
     * Administración y configuración
   - Requisitos no funcionales (rendimiento, seguridad, escalabilidad)
   - Restricciones técnicas y supuestos
   - Fases de entrega con criterios de aceptación por fase

El stack tecnológico es: Spring Boot 3.3 + React 18 + TypeScript + SQL Server 2022.
La arquitectura es hexagonal (Domain / Application / Infrastructure) con multi-tenancy por company_id.
```

---

*Sesión: AI4Devs Design — RecruitFlow*
