# PRD — RecruitFlow
**Product Requirements Document**
**Versión:** 1.0
**Fecha:** 2026-04-04
**Autor:** Product Manager (Senior)
**Estado:** Draft

---

## Tabla de contenidos

1. [Contexto y problema a resolver](#1-contexto-y-problema-a-resolver)
2. [Objetivos medibles](#2-objetivos-medibles)
3. [Público objetivo y personas](#3-público-objetivo-y-personas)
4. [User Stories principales](#4-user-stories-principales)
5. [Requisitos funcionales](#5-requisitos-funcionales)
6. [Requisitos no funcionales](#6-requisitos-no-funcionales)
7. [Criterios de éxito](#7-criterios-de-éxito)
8. [Fuera de alcance (v1)](#8-fuera-de-alcance-v1)
9. [Riesgos y dependencias](#9-riesgos-y-dependencias)

---

## 1. Contexto y problema a resolver

### 1.1 Contexto del mercado

Las empresas de selección de personal (ETTs, headhunters, consultoras de RRHH) operan en un mercado donde el tiempo de respuesta al cliente y la precisión en el match de candidatos son ventajas competitivas directas. Un cliente que solicita un perfil espera una terna cualificada en el menor tiempo posible; la primera empresa que entregue el candidato correcto gana la colocación.

### 1.2 Problema principal

Las empresas de selección gestionan simultáneamente múltiples posiciones para múltiples clientes con herramientas fragmentadas: hojas Excel para tracking, correos electrónicos para comunicación, LinkedIn para búsqueda y bases de datos locales desconectadas. Esto genera:

- **Lentitud en el matching:** Los recruiters buscan manualmente en su base de datos interna candidato a candidato, perdiendo horas en cada proceso.
- **Pérdida de talento previo:** Candidatos entrevistados en procesos anteriores quedan "enterrados" en archivos locales y no se reutilizan para nuevas posiciones.
- **Matching impreciso:** La evaluación de skills se realiza de forma subjetiva y no estructurada, generando propuestas de candidatos que no encajan con los requisitos del cliente.
- **Sin visibilidad del pipeline:** Los managers no tienen visión en tiempo real de cuántos candidatos hay en cada etapa para cada cliente.
- **Comunicación ineficiente:** Las actualizaciones de estado al cliente se hacen de forma manual y reactiva, dañando la percepción de profesionalidad.

### 1.3 Hipótesis de solución

Un sistema ATS especializado para empresas de selección que priorice:
1. **Speed-to-match:** motor de matching automático entre perfiles de candidatos y requisitos de cada posición del cliente.
2. **Reutilización del talento:** base de datos de candidatos estructurada y buscable con skills etiquetadas.
3. **Visibilidad cliente:** portal o reporte automático de estado del proceso.

---

## 2. Objetivos medibles

### OKRs del producto (horizonte 12 meses post-lanzamiento)

| Objetivo | Key Result | Métrica base (estimada) | Meta |
|---|---|---|---|
| Reducir el tiempo de entrega de candidatos al cliente | Time-to-shortlist (días desde apertura de posición hasta envío de terna) | 12 días | ≤ 5 días |
| Mejorar la precisión del matching | % de candidatos propuestos que avanzan a entrevista con el cliente | 35% | ≥ 60% |
| Maximizar reutilización de la base de talento | % de colocaciones cerradas con candidatos ya existentes en la BD | 20% | ≥ 45% |
| Incrementar la satisfacción del cliente | NPS de clientes activos | No medido | ≥ 40 |
| Aumentar la productividad del recruiter | Número de posiciones activas gestionadas por recruiter | 8 | ≥ 15 |

---

## 3. Público objetivo y personas

### Persona 1 — Laura, Recruiter Senior (usuario principal)
- **Rol:** Gestiona 8-12 posiciones activas simultáneas para distintos clientes
- **Dolor:** Pierde 3-4 horas diarias buscando candidatos manualmente y actualizando estados en Excel
- **Necesita:** Ver rápidamente qué candidatos de su BD tienen los skills requeridos para una nueva posición
- **Mide su éxito en:** Colocaciones cerradas al mes y velocidad de entrega

### Persona 2 — Carlos, Director de RRHH / Manager de Equipo (usuario gestor)
- **Rol:** Supervisa un equipo de 5-10 recruiters y gestiona la relación comercial con clientes
- **Dolor:** No tiene visibilidad del pipeline de cada proceso; se entera de los problemas tarde
- **Necesita:** Dashboard en tiempo real del estado de todos los procesos activos
- **Mide su éxito en:** Revenue por colocación, ratio de cierre, satisfacción del cliente

### Persona 3 — Elena, Responsable de RRHH en empresa cliente (usuario externo)
- **Rol:** Solicita perfiles a la consultora y espera candidatos cualificados rápidamente
- **Dolor:** Falta de visibilidad del proceso, comunicación reactiva, candidatos mal alineados con sus necesidades
- **Necesita:** Saber en qué estado está su proceso y poder dar feedback estructurado sobre candidatos
- **Mide su éxito en:** Tiempo hasta cubrir la vacante, calidad de los candidatos recibidos

### Persona 4 — Marcos, Candidato (usuario final del proceso)
- **Rol:** Profesional en búsqueda activa o pasiva de empleo
- **Dolor:** No recibe feedback de sus candidaturas, no sabe en qué estado está su proceso
- **Necesita:** Comunicación clara y transparente sobre su estado en cada proceso

---

## 4. User Stories principales

### Épica 1 — Gestión de posiciones del cliente

**US-01**
> Como recruiter, quiero crear una nueva posición a partir de los requisitos del cliente, incluyendo skills obligatorios y deseables con su peso relativo, para que el sistema pueda hacer matching automático desde el primer momento.

**Criterios de aceptación:**
- Puedo definir skills como obligatorios (must-have) o deseables (nice-to-have) con un peso del 1-5
- El sistema sugiere skills estandarizadas de un catálogo mientras escribo (autocompletado)
- La posición queda vinculada al cliente y al recruiter responsable
- Se genera automáticamente un código único de posición

---

**US-02**
> Como manager, quiero ver en un dashboard el estado de todas las posiciones activas de mi equipo (por cliente, recruiter, etapa y días abiertos), para identificar cuellos de botella y actuar antes de que afecten la relación con el cliente.

**Criterios de aceptación:**
- Vista kanban y vista tabla, filtrables por recruiter y cliente
- Alerta visual cuando una posición lleva más de N días sin movimiento (configurable)
- Drill-down a detalle de posición con un clic

---

### Épica 2 — Motor de matching y base de talento

**US-03**
> Como recruiter, quiero que al abrir una nueva posición el sistema me muestre automáticamente los candidatos de la base de datos ordenados por porcentaje de match con los requisitos definidos, para no tener que buscar manualmente.

**Criterios de aceptación:**
- El ranking aparece en menos de 3 segundos para bases de hasta 50.000 candidatos
- El score de matching se muestra como porcentaje con desglose por skill
- Puedo filtrar el ranking por disponibilidad, ubicación y pretensión salarial
- Los candidatos con match ≥ 80% se destacan visualmente

---

**US-04**
> Como recruiter, quiero registrar y actualizar el perfil de un candidato incluyendo sus skills con nivel de dominio (básico/medio/avanzado/experto), para que el motor de matching tenga datos precisos con los que trabajar.

**Criterios de aceptación:**
- El sistema parsea el CV automáticamente y propone skills detectadas para confirmación
- Puedo añadir o corregir skills manualmente
- El historial de procesos anteriores del candidato es visible en su perfil
- Los skills se mapean al catálogo estandarizado del sistema

---

**US-05**
> Como recruiter, quiero buscar candidatos usando lenguaje natural (ej.: "desarrollador Python con experiencia en AWS y disponibilidad inmediata en Madrid"), para encontrar perfiles rápidamente sin tener que navegar por filtros complejos.

**Criterios de aceptación:**
- La búsqueda interpreta la query y la traduce a filtros estructurados
- Los resultados aparecen en menos de 2 segundos
- Puedo ver qué parte de mi query se aplicó como filtro
- Puedo guardar búsquedas frecuentes

---

### Épica 3 — Pipeline de selección y colaboración

**US-06**
> Como recruiter, quiero mover candidatos entre etapas del pipeline (preseleccionado, contactado, entrevista interna, propuesto al cliente, entrevista cliente, oferta, contratado/descartado) con un registro automático de fecha y motivo, para tener trazabilidad completa del proceso.

**Criterios de aceptación:**
- Movimiento de etapa con drag & drop o mediante botón de acción
- Al descartar, es obligatorio seleccionar un motivo de descarte de un listado predefinido
- Se registra automáticamente quién movió al candidato, cuándo y desde qué etapa
- El historial de etapas es visible en la ficha del candidato

---

**US-07**
> Como recruiter, quiero registrar notas de entrevista y evaluaciones estructuradas por competencias para cada candidato en cada proceso, para que mis compañeros y el manager tengan contexto completo sin tener que preguntarme.

**Criterios de aceptación:**
- Formulario de evaluación configurable por tipo de posición
- Puntuación numérica por competencia + campo de texto libre
- Las evaluaciones son visibles para el equipo con control de permisos
- Se puede adjuntar archivo (grabación, prueba técnica, etc.)

---

### Épica 4 — Comunicación con cliente y candidato

**US-08**
> Como recruiter, quiero enviar al cliente una terna de candidatos desde la plataforma con su perfil resumido y mi evaluación, para que pueda revisarlos y dar feedback estructurado sin necesidad de intercambiar PDFs por email.

**Criterios de aceptación:**
- Generación de un microsite o enlace seguro con los perfiles propuestos
- El cliente puede votar cada candidato (interesado / no interesado / ver más info) sin necesidad de login
- El feedback del cliente queda registrado automáticamente en el pipeline
- El recruiter recibe notificación cuando el cliente da feedback

---

**US-09**
> Como candidato, quiero recibir comunicaciones automáticas que me informen del estado de mi candidatura en cada transición de etapa relevante, para no tener que preguntar y tener una experiencia profesional con la consultora.

**Criterios de aceptación:**
- Plantillas de email personalizables por etapa y por cliente
- El recruiter puede activar/desactivar comunicaciones por etapa
- Las comunicaciones incluyen el nombre del candidato y el nombre de la posición
- Se registra log de comunicaciones enviadas en la ficha del candidato

---

### Épica 5 — Reporting y analytics

**US-10**
> Como manager, quiero un informe mensual automatizado con las métricas clave de mi equipo (posiciones abiertas/cerradas, time-to-hire, ratio de propuesta-aceptación, fuente de candidatos) para presentarlo al cliente y tomar decisiones de mejora.

**Criterios de aceptación:**
- Informe generado en PDF/exportable a Excel con un clic
- Filtrable por cliente, recruiter y rango de fechas
- Incluye comparativa con el mes anterior
- Se puede programar envío automático por email

---

## 5. Requisitos funcionales

### RF-01 — Gestión de posiciones
- Creación de posiciones con: cliente, título, descripción, skills (must/nice-to-have + peso), rango salarial, ubicación, modalidad, urgencia y recruiter asignado
- Estados de posición: Borrador → Activa → En proceso → Cerrada (cubierta/cancelada)
- Clonación de posición para reutilizar configuración de skills

### RF-02 — Catálogo de skills estandarizado
- Catálogo maestro de skills editable por administradores
- Agrupación por categorías (lenguajes, frameworks, soft skills, sectores, idiomas, etc.)
- Sinónimos mapeados (ej.: "JS" → "JavaScript") para normalizar el matching
- Nivel de dominio: Básico / Medio / Avanzado / Experto

### RF-03 — Motor de matching
- Algoritmo de scoring basado en: presencia de skills must-have (bloqueo si ausencia), peso de skills nice-to-have, nivel de dominio declarado vs. requerido, experiencia total en años, disponibilidad
- Score visible como porcentaje global + desglose por skill
- Ranking actualizado en tiempo real al modificar requisitos de la posición

### RF-04 — Gestión de candidatos
- Ficha de candidato con: datos personales, CV adjunto, skills + nivel, historial de procesos, notas y evaluaciones, estado de disponibilidad y pretensión salarial
- Parsing automático de CV (PDF/Word) con propuesta de skills para validación
- Deduplicación automática por email y teléfono al importar nuevos candidatos
- Importación masiva de candidatos (CSV, LinkedIn export)

### RF-05 — Pipeline de selección
- Pipeline kanban configurable con etapas predefinidas y personalizables
- Registro automático de cambios de etapa (quién, cuándo, desde/hasta)
- Motivos de descarte obligatorios y configurables
- Vista de pipeline por posición y vista global de todos los candidatos activos

### RF-06 — Comunicaciones
- Editor de plantillas de email con variables dinámicas ({{nombre_candidato}}, {{posicion}}, {{empresa_cliente}}, etc.)
- Envío manual y automático por transición de etapa
- Log de comunicaciones en ficha de candidato
- Integración SMTP propia o con Gmail/Outlook vía OAuth

### RF-07 — Portal de propuesta al cliente
- Generación de enlace único y seguro por terna de candidatos
- Perfiles simplificados (anonimizables para proteger datos hasta confirmación de interés)
- Feedback estructurado del cliente (interesado/no interesado + comentario)
- Expiración configurable del enlace

### RF-08 — Reporting
- Dashboard en tiempo real: posiciones activas, candidatos por etapa, alertas de posiciones estancadas
- Métricas: time-to-shortlist, time-to-hire, ratio propuesta-aceptación por recruiter/cliente, source of hire
- Exportación PDF y Excel
- Programación de envío de informes por email

### RF-09 — Gestión de usuarios y permisos
- Roles: Administrador, Manager, Recruiter, Cliente (solo lectura/feedback)
- Permisos granulares por cliente y por posición
- SSO con Google Workspace y Microsoft 365
- Log de actividad de usuarios (auditoría)

### RF-10 — Integraciones
- API REST documentada para integraciones con sistemas del cliente
- Webhooks configurables por evento (nuevo candidato, cambio de etapa, feedback de cliente)
- Integración nativa con LinkedIn (importación de perfil con un clic)
- Zapier/Make connector para automatizaciones sin código

---

## 6. Requisitos no funcionales

### Rendimiento
- **RNF-01:** El motor de matching debe retornar resultados en < 3 segundos para bases de hasta 100.000 candidatos
- **RNF-02:** El tiempo de carga de cualquier página no debe superar 2 segundos en conexión estándar (10 Mbps)
- **RNF-03:** El sistema debe soportar hasta 500 usuarios concurrentes sin degradación

### Disponibilidad y fiabilidad
- **RNF-04:** SLA de disponibilidad del 99,5% mensual (excluye mantenimientos programados)
- **RNF-05:** Backups automáticos diarios con retención de 30 días
- **RNF-06:** Tiempo de recuperación ante desastre (RTO) < 4 horas

### Seguridad y privacidad
- **RNF-07:** Cumplimiento GDPR/LOPD: gestión de consentimiento, derecho al olvido, exportación de datos del candidato
- **RNF-08:** Cifrado en tránsito (TLS 1.2+) y en reposo (AES-256)
- **RNF-09:** Autenticación multifactor (MFA) disponible para todos los usuarios
- **RNF-10:** Datos de candidatos anonimizados automáticamente tras 2 años de inactividad (configurable)
- **RNF-11:** Los enlaces de propuesta al cliente deben ser de un solo uso o con expiración configurable y acceso auditado

### Escalabilidad
- **RNF-12:** Arquitectura multi-tenant: cada empresa cliente tiene su espacio de datos aislado
- **RNF-13:** La BD de candidatos debe escalar hasta 500.000 registros sin degradación de rendimiento

### Usabilidad
- **RNF-14:** Un recruiter nuevo debe ser capaz de crear su primera posición y obtener un ranking de matching en menos de 15 minutos sin formación
- **RNF-15:** Interfaz responsive que funcione en tablet y móvil para consultas en movilidad
- **RNF-16:** Soporte de idiomas: español e inglés en v1; estructura preparada para i18n

### Mantenibilidad
- **RNF-17:** Cobertura de tests automatizados ≥ 80% en capa de negocio (motor de matching, pipeline, comunicaciones)
- **RNF-18:** API versionada para garantizar compatibilidad hacia atrás durante 12 meses

---

## 7. Criterios de éxito

### Criterios de lanzamiento (Go/No-Go para MVP)

| Criterio | Condición mínima |
|---|---|
| Motor de matching operativo | Retorna ranking con score en < 3s para BD de prueba de 10.000 candidatos |
| Pipeline completo | Flujo completo desde creación de posición hasta propuesta al cliente funcional end-to-end |
| Parsing de CV | Tasa de extracción correcta de skills ≥ 70% en conjunto de prueba de 100 CVs |
| Comunicaciones | Envío de emails automáticos por cambio de etapa funcional en todos los entornos de email testeados |
| Seguridad | Auditoría de seguridad básica superada (OWASP Top 10) |
| Performance | Criterios RNF-01 y RNF-02 cumplidos en entorno de staging |

### Criterios de éxito a 3 meses post-lanzamiento

| Métrica | Umbral mínimo | Objetivo |
|---|---|---|
| Adopción activa | ≥ 70% de recruiters del cliente usan la plataforma ≥ 3 veces/semana | ≥ 90% |
| Time-to-shortlist | Reducción del 30% vs. baseline pre-implementación | Reducción del 50% |
| Match quality | ≥ 50% de candidatos propuestos avanzan a entrevista con el cliente | ≥ 60% |
| NPS interno (recruiters) | ≥ 20 | ≥ 35 |
| Soporte | < 5 tickets críticos/semana por empresa cliente | < 2 tickets críticos/semana |

### Criterios de éxito a 12 meses post-lanzamiento

| Métrica | Objetivo |
|---|---|
| Retención de clientes (churn) | < 10% anual |
| NPS de clientes (empresas de selección) | ≥ 40 |
| Time-to-hire promedio de clientes activos | ≤ 5 días (time-to-shortlist) |
| % colocaciones con talento reutilizado de BD | ≥ 45% |
| Expansión de seats (upsell) | ≥ 30% de clientes amplían licencias en 12 meses |

---

## 8. Fuera de alcance (v1)

Los siguientes elementos quedan explícitamente fuera del MVP y serán considerados en versiones futuras:

- **Videoentrevistas integradas:** Se recomienda integración con Zoom/Teams pero no plataforma propia de video
- **Assessments y pruebas técnicas:** Motor de tests y coding challenges
- **Portal de candidato self-service:** El candidato no tiene login propio en v1; solo recibe emails
- **App móvil nativa:** v1 será web responsive; app nativa queda para v2
- **IA generativa para JDs:** Generación automática de descripciones de posición
- **Marketplace de job boards de pago:** Integraciones con portales de pago (InfoJobs Premium, etc.)
- **Módulo de onboarding:** Gestión del proceso tras la contratación
- **Facturación y gestión de honorarios:** Integración con sistemas contables

---

## 9. Riesgos y dependencias

| Riesgo | Probabilidad | Impacto | Mitigación |
|---|---|---|---|
| Baja calidad del parsing de CV impacta precisión del matching | Alta | Alto | Validación humana obligatoria de skills parseadas; mejora iterativa del parser |
| Resistencia al cambio por parte de los recruiters | Media | Alto | Onboarding guiado, formación, quick wins visibles en primeras semanas |
| Datos de candidatos inconsistentes en la migración inicial | Alta | Medio | Proceso de limpieza de datos antes de la migración; herramienta de deduplicación |
| Dependencia de LinkedIn para importación de perfiles | Media | Medio | Importación via CSV como alternativa; monitorizar cambios en API de LinkedIn |
| Incumplimiento GDPR por gestión inadecuada de consentimientos | Baja | Muy alto | Revisión legal antes del lanzamiento; DPO involucrado desde el diseño |
| Motor de matching no suficientemente preciso en v1 | Media | Alto | A/B testing del algoritmo; feedback loop de recruiters para ajuste de pesos |

---

*Documento sujeto a revisión y aprobación del equipo de producto, engineering y stakeholders de negocio.*
*Próxima revisión programada: 2026-05-04*
