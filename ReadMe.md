# 🔄 RecruitFlow — Aplicación de Gestión del Proceso de Reclutamiento

## Descripción General

**RecruitFlow** es una aplicación web para gestionar de extremo a extremo el ciclo de reclutamiento de personal dentro de una organización. Basada en un flujo circular de 7 etapas, permite a los equipos de Recursos Humanos crear vacantes, publicarlas, recibir y revisar candidaturas, aplicar pruebas, programar entrevistas y finalmente contratar a los candidatos seleccionados.

---

## 🎯 Objetivo

Centralizar y digitalizar todo el proceso de selección de personal, proporcionando trazabilidad completa desde la creación de una oferta de empleo hasta la contratación del candidato, eliminando procesos manuales dispersos en correos, hojas de cálculo o herramientas desconectadas.

---

## 🔁 Flujo del Proceso (7 Etapas)

El proceso sigue un ciclo continuo y ordenado:

```
1. Crear Oferta  →  2. Publicar  →  3. Recibir Solicitudes
        ↑                                        ↓
7. Contratar     ←  6. Entrevistar  ←  5. Pruebas Online  ←  4. Revisar Candidaturas
```

### Etapa 1 — Creación de Ofertas de Empleo
- Formulario para definir el puesto: título, descripción, requisitos, salario y departamento.
- Asignación de responsable de reclutamiento.
- Definición de fechas límite de aplicación.
- Estado: `Borrador` | `Lista para publicar`

### Etapa 2 — Publicación en Canales
- Publicación simultánea o selectiva en: tableros de empleo (LinkedIn, InfoJobs, Indeed), web corporativa y redes sociales.
- Gestión centralizada de los canales activos por vacante.
- Seguimiento de rendimiento por canal (número de aplicaciones recibidas por fuente).

### Etapa 3 — Recepción de Solicitudes
- Buzón centralizado de candidaturas para cada vacante.
- Carga automática de CVs y cartas de presentación.
- Registro de la fecha, canal de origen y datos del candidato.
- Detección de duplicados (mismo candidato en múltiples canales).

### Etapa 4 — Revisión de Candidaturas
- Panel de revisión con filtros por palabras clave, experiencia, formación y puntuación.
- Funcionalidad de descarte, marcado como favorito o avance a la siguiente etapa.
- Comentarios internos del equipo sobre cada candidato.
- Estados: `Pendiente` | `En revisión` | `Descartado` | `Avanzado`

### Etapa 5 — Pruebas Online
- Asignación de pruebas técnicas o psicométricas al candidato por correo.
- Integración con plataformas de evaluación externas o módulo propio de cuestionarios.
- Registro automático de resultados y puntuaciones.
- Umbral configurable para avance automático a entrevistas.

### Etapa 6 — Programación de Entrevistas
- Calendario integrado para agendar entrevistas (presencial, telefónica o videoconferencia).
- Envío automático de invitaciones al candidato y al entrevistador.
- Recordatorios automáticos previos a la entrevista.
- Registro del resultado y feedback post-entrevista.

### Etapa 7 — Contratación
- Generación de la oferta formal al candidato seleccionado.
- Registro del estado de aceptación o rechazo de la oferta.
- Notificación automática a candidatos no seleccionados.
- Cierre de la vacante y archivo del proceso para auditoría.

---

## 👤 Roles de Usuario

| Rol | Permisos |
|-----|----------|
| **Administrador RRHH** | Acceso total: crear vacantes, gestionar candidatos, contratar |
| **Reclutador** | Gestionar candidaturas, programar pruebas y entrevistas |
| **Hiring Manager** | Ver candidatos avanzados, dejar feedback de entrevistas |
| **Candidato** | Aplicar a vacantes, completar pruebas, consultar estado de su candidatura |

---

## 📊 Panel de Control (Dashboard)

- Visión general del estado de todas las vacantes activas.
- Métricas clave: tiempo promedio de contratación, tasa de conversión por etapa, fuente más efectiva.
- Gráfico del embudo de reclutamiento (cuántos candidatos hay en cada etapa).
- Alertas de vacantes con poca actividad o próximas a vencer.

---

## 🔔 Notificaciones y Comunicaciones

- Correos automáticos en cada transición de etapa (al candidato y al responsable).
- Plantillas de correo personalizables por etapa.
- Notificaciones internas en la plataforma para el equipo de RRHH.

---

## 🔗 Integraciones

| Sistema | Propósito |
|---------|-----------|
| LinkedIn / Indeed / InfoJobs | Publicación de ofertas |
| Google Calendar / Outlook | Programación de entrevistas |
| Plataformas de evaluación (TestGorilla, etc.) | Pruebas online |
| HRIS / ERP (SAP, Workday, etc.) | Sincronización del empleado contratado |
| Firma digital (DocuSign, etc.) | Firma de oferta de empleo |

---

## 🛠️ Stack Tecnológico (Recomendado)

```
Frontend:   React + TypeScript + Tailwind CSS
Backend:    Node.js + Express / Python + FastAPI
Base de datos: PostgreSQL
Auth:       OAuth 2.0 / JWT
Hosting:    AWS / Azure / GCP
```

---

## 📁 Estructura de Módulos

```
recruitflow/
├── vacantes/          # Gestión de ofertas de empleo
├── candidatos/        # Base de datos de candidatos
├── candidaturas/      # Solicitudes por vacante
├── pruebas/           # Módulo de evaluaciones online
├── entrevistas/       # Agenda y resultados de entrevistas
├── contrataciones/    # Registro de ofertas y altas
├── dashboard/         # Métricas e indicadores
├── configuracion/     # Usuarios, roles, plantillas, canales
└── integraciones/     # Conectores con sistemas externos
```

---

## ✅ Criterios de Aceptación Funcionales

- [ ] Un reclutador puede crear una vacante y publicarla en al menos 2 canales en menos de 5 minutos.
- [ ] El sistema centraliza candidaturas de todos los canales en una única vista por vacante.
- [ ] Un candidato puede completar una prueba online desde el enlace recibido por correo sin necesidad de registro previo.
- [ ] El sistema impide avanzar a un candidato a la siguiente etapa sin completar la actual.
- [ ] Todas las transiciones de etapa quedan registradas con fecha, hora y usuario responsable.
- [ ] El dashboard se actualiza en tiempo real con el estado de cada proceso.

---

## 📌 Versión

`v1.0.0 — MVP` | Alcance inicial: etapas 1 a 7 para un único puesto de trabajo simultáneo por organización.

---

## 📂 Documentación del proyecto

```
docs/
├── product/
│   ├── vision.md               # Visión del producto y análisis de mercado
│   ├── PRD.md                  # Product Requirements Document
│   └── casos-de-uso.md         # 44 casos de uso con diagramas Mermaid
│
├── architecture/
│   ├── project.md              # Arquitectura completa: C4, hexagonal, Docker, API contracts
│   ├── data-model.md           # Modelo de datos — 16 entidades base (erDiagram)
│   ├── data-model-additional.md # 15 entidades adicionales
│   └── openapi.yaml            # Especificación OpenAPI 3.0 completa
│
├── security/
│   └── security-design.md      # RBAC, JWT, CORS, rate limiting, tenant isolation
│
└── quality/
    ├── testing-strategy.md     # Pirámide 80/15/5, JUnit 5, Jest, Cypress, CI/CD
    └── code-style-guide.md     # Google Style + OWASP, naming conventions

config/
├── checkstyle/
│   ├── checkstyle.xml          # Copiar a backend/config/checkstyle/
│   └── suppressions.xml
└── frontend/
    ├── .eslintrc.cjs           # Copiar a frontend/
    ├── .prettierrc
    ├── .prettierignore
    └── .editorconfig
```

---

*Documento funcional generado para el proyecto RecruitFlow.*
