# Casos de Uso — RecruitFlow
**Versión:** 1.0
**Fecha:** 2026-04-04
**Basado en:** ReadMe.md · PRD-RecruitFlow.md · recruitflow-vision-producto.md

---

## Tabla de contenidos

1. [Actores del sistema](#1-actores-del-sistema)
2. [Diagrama general de casos de uso](#2-diagrama-general-de-casos-de-uso)
3. [Módulo: Gestión de Vacantes](#3-módulo-gestión-de-vacantes)
4. [Módulo: Base de Talento y Matching](#4-módulo-base-de-talento-y-matching)
5. [Módulo: Pipeline de Selección](#5-módulo-pipeline-de-selección)
6. [Módulo: Pruebas y Evaluaciones](#6-módulo-pruebas-y-evaluaciones)
7. [Módulo: Entrevistas](#7-módulo-entrevistas)
8. [Módulo: Propuesta al Cliente](#8-módulo-propuesta-al-cliente)
9. [Módulo: Comunicaciones](#9-módulo-comunicaciones)
10. [Módulo: Reporting y Dashboard](#10-módulo-reporting-y-dashboard)
11. [Módulo: Administración del Sistema](#11-módulo-administración-del-sistema)
12. [Flujo end-to-end del proceso de selección](#12-flujo-end-to-end-del-proceso-de-selección)

---

## 1. Actores del sistema

| Actor | Tipo | Descripción |
|---|---|---|
| **Administrador RRHH** | Interno primario | Control total del sistema: configuración, usuarios, catálogos |
| **Manager** | Interno primario | Supervisión de equipo, visibilidad de todos los procesos, reporting |
| **Recruiter** | Interno primario | Actor central: gestiona vacantes, candidatos y pipeline diariamente |
| **Hiring Manager (cliente)** | Externo | Da feedback sobre candidatos propuestos vía portal seguro |
| **Candidato** | Externo | Recibe comunicaciones, completa pruebas, consulta estado |
| **Sistema de Email (SMTP/OAuth)** | Sistema externo | Entrega de comunicaciones automáticas |
| **Job Boards** | Sistema externo | LinkedIn, Indeed, InfoJobs — publicación de ofertas |
| **Plataforma de Evaluación** | Sistema externo | TestGorilla u otras — pruebas técnicas/psicométricas |
| **Calendario** | Sistema externo | Google Calendar / Outlook — programación de entrevistas |
| **HRIS/ERP** | Sistema externo | SAP, Workday — sincronización al contratar |

---

## 2. Diagrama general de casos de uso

```mermaid
graph TB
    subgraph ActoresInternos
        ADM([Administrador RRHH])
        MGR([Manager])
        REC([Recruiter])
    end

    subgraph ActoresExternos
        HM([Hiring Manager])
        CAN([Candidato])
    end

    subgraph SistemasExternos
        EMAIL([Sistema Email])
        JB([Job Boards])
        EVAL([Plataforma Evaluacion])
        CAL([Calendario])
        HRIS([HRIS ERP])
    end

    subgraph RF
        GV[Gestion de Vacantes]
        BT[Base de Talento y Matching]
        PL[Pipeline de Seleccion]
        PR[Pruebas y Evaluaciones]
        EN[Gestion de Entrevistas]
        PC[Propuesta al Cliente]
        COM[Comunicaciones]
        RPT[Reporting y Dashboard]
        ADMS[Administracion del Sistema]
    end

    ADM --> ADMS
    ADM --> GV
    MGR --> RPT
    MGR --> PL
    REC --> GV
    REC --> BT
    REC --> PL
    REC --> PR
    REC --> EN
    REC --> PC
    REC --> COM
    HM --> PC
    CAN --> PR
    CAN --> COM

    GV <--> JB
    COM <--> EMAIL
    PR <--> EVAL
    EN <--> CAL
    PL --> HRIS
```

---

## 3. Módulo: Gestión de Vacantes

### Diagrama de casos de uso

```mermaid
flowchart LR
    REC([Recruiter])
    ADM([Administrador RRHH])
    MGR([Manager])
    JB([Job Boards])

    subgraph GV
        UC1[UC-01 Crear vacante]
        UC2[UC-02 Editar vacante]
        UC3[UC-03 Clonar vacante]
        UC4[UC-04 Publicar en canales]
        UC5[UC-05 Gestionar estados]
        UC6[UC-06 Cerrar vacante]
        UC7[UC-07 Consultar vacantes]
    end

    REC --> UC1
    REC --> UC2
    REC --> UC3
    REC --> UC4
    REC --> UC5
    REC --> UC6
    ADM --> UC1
    ADM --> UC6
    MGR --> UC7
    UC4 <--> JB
```

### Detalle de casos de uso

#### UC-01 — Crear vacante

| Campo | Detalle |
|---|---|
| **Actor principal** | Recruiter / Administrador RRHH |
| **Precondición** | Usuario autenticado con rol Recruiter o superior |
| **Trigger** | El cliente solicita cubrir una posición |
| **Flujo principal** | 1. El recruiter accede a "Nueva vacante" 2. Rellena: título, descripción, cliente, departamento, ubicación, modalidad, rango salarial, fecha límite 3. Define skills: selecciona del catálogo, marca must-have / nice-to-have, asigna peso (1-5) 4. Asigna recruiter responsable 5. Guarda como Borrador o pasa a Activa |
| **Flujo alternativo** | Si clona desde vacante existente (UC-03), los campos se precargan |
| **Postcondición** | Vacante creada con código único; motor de matching disponible inmediatamente |
| **Reglas de negocio** | Debe tener al menos 1 skill must-have para activar matching automático |

#### UC-04 — Publicar en canales

| Campo | Detalle |
|---|---|
| **Actor principal** | Recruiter |
| **Precondición** | Vacante en estado Activa |
| **Flujo principal** | 1. El recruiter selecciona canales de publicación (LinkedIn, Indeed, InfoJobs, web corporativa) 2. Revisa la vista previa del anuncio por canal 3. Confirma publicación simultánea o programada 4. El sistema publica vía API y registra fecha y canal |
| **Postcondición** | Vacante publicada; se inicia tracking de candidaturas por fuente |
| **Excepción** | Si falla la API de un canal, se notifica al recruiter y se reintenta en 30 min |

#### UC-05 — Gestionar estados de vacante

```mermaid
stateDiagram-v2
    [*] --> Borrador : Crear vacante
    Borrador --> Activa : Activar
    Activa --> EnProceso : Primera candidatura recibida
    EnProceso --> Cerrada_Cubierta : Candidato contratado
    EnProceso --> Cerrada_Cancelada : Cliente cancela
    Activa --> Cerrada_Cancelada : Cliente cancela
    Cerrada_Cubierta --> [*]
    Cerrada_Cancelada --> [*]
    Borrador --> Borrador : Editar
    Activa --> Activa : Editar / Republicar
```

---

## 4. Módulo: Base de Talento y Matching

### Diagrama de casos de uso

```mermaid
flowchart LR
    REC([Recruiter])
    ADM([Administrador RRHH])
    LI([LinkedIn / CSV])

    subgraph BT
        UC10[UC-10 Registrar candidato]
        UC11[UC-11 Importar desde CV]
        UC12[UC-12 Importar masivamente]
        UC13[UC-13 Validar skills parseadas]
        UC14[UC-14 Buscar candidatos]
        UC15[UC-15 Ranking de matching]
        UC16[UC-16 Ver ficha candidato]
        UC17[UC-17 Gestionar catalogo skills]
        UC18[UC-18 Anonimizar candidato]
    end

    REC --> UC10
    REC --> UC11
    REC --> UC12
    REC --> UC13
    REC --> UC14
    REC --> UC15
    REC --> UC16
    ADM --> UC17
    ADM --> UC18
    UC11 --> LI
    UC12 --> LI
```

### Detalle de casos de uso

#### UC-11 — Importar candidato desde CV

| Campo | Detalle |
|---|---|
| **Actor principal** | Recruiter |
| **Flujo principal** | 1. Recruiter sube PDF o Word del CV 2. El parser extrae: nombre, contacto, experiencia, formación, skills detectadas 3. El sistema mapea skills al catálogo estandarizado y muestra propuesta 4. Recruiter valida, corrige o añade skills 5. Se verifica duplicado por email/teléfono 6. Candidato queda registrado en la BD |
| **Flujo alternativo** | Si el candidato ya existe, se ofrece fusionar perfiles o actualizar datos |
| **Postcondición** | Candidato disponible para matching inmediato |

#### UC-15 — Obtener ranking de matching

| Campo | Detalle |
|---|---|
| **Actor principal** | Recruiter (trigger automático al crear/modificar vacante) |
| **Precondición** | Vacante con al menos 1 skill must-have definida |
| **Flujo principal** | 1. El sistema recupera todos los candidatos de la BD 2. Calcula score por candidato: bloqueo si falta must-have, suma ponderada de nice-to-have, ajuste por nivel de dominio vs. requerido, ajuste por disponibilidad 3. Ordena el ranking de mayor a menor score 4. Presenta lista con score % global y desglose por skill 5. Candidatos ≥ 80% se marcan visualmente como "Top match" |
| **Rendimiento** | Resultado en < 3 segundos para 100.000 candidatos |
| **Filtros adicionales** | Disponibilidad, ubicación, pretensión salarial, fecha de última actualización |

```mermaid
flowchart TD
    A[Vacante creada / modificada] --> B{¿Tiene skills must-have?}
    B -- No --> C[Matching desactivado\nNotificar recruiter]
    B -- Sí --> D[Recuperar candidatos de la BD]
    D --> E{¿Candidato tiene\ntodos los must-have?}
    E -- No --> F[Score = 0\nExcluido del ranking]
    E -- Sí --> G[Calcular puntuación nice-to-have\nponderada por peso y nivel]
    G --> H[Ajustar por disponibilidad\ny pretensión salarial]
    H --> I[Score final %]
    I --> J{Score ≥ 80%?}
    J -- Sí --> K[Marcar como Top Match]
    J -- No --> L[Incluir en ranking estándar]
    K --> M[Presentar ranking ordenado]
    L --> M
    F --> N[Lista de excluidos disponible\npara referencia]
```

#### UC-17 — Gestionar catálogo de skills

| Campo | Detalle |
|---|---|
| **Actor principal** | Administrador RRHH |
| **Flujo principal** | 1. Admin accede al catálogo maestro 2. Crea / edita / desactiva skills 3. Define categoría (lenguaje, framework, soft skill, idioma, sector…) 4. Configura sinónimos (JS → JavaScript) 5. Los cambios se propagan automáticamente al motor de matching |

---

## 5. Módulo: Pipeline de Selección

### Diagrama de casos de uso

```mermaid
flowchart LR
    REC([Recruiter])
    MGR([Manager])
    HM([Hiring Manager])
    HRIS([HRIS / ERP])

    subgraph PL
        UC20[UC-20 Añadir candidato]
        UC21[UC-21 Mover entre etapas]
        UC22[UC-22 Descartar candidato]
        UC23[UC-23 Registrar evaluacion]
        UC24[UC-24 Ver historial etapas]
        UC25[UC-25 Ver pipeline global]
        UC26[UC-26 Registrar contratacion]
        UC27[UC-27 Configurar etapas]
    end

    REC --> UC20
    REC --> UC21
    REC --> UC22
    REC --> UC23
    REC --> UC24
    MGR --> UC25
    MGR --> UC27
    REC --> UC26
    UC26 --> HRIS
    HM --> UC23
```

### Estados del candidato en el pipeline

```mermaid
stateDiagram-v2
    [*] --> Preseleccionado : Añadir desde ranking de matching
    Preseleccionado --> Contactado : Recruiter contacta
    Contactado --> EntrevistaInterna : Candidato responde positivo
    EntrevistaInterna --> PropuestoAlCliente : Entrevista superada
    PropuestoAlCliente --> EntrevistaCliente : Cliente interesado
    EntrevistaCliente --> Oferta : Entrevista cliente superada
    Oferta --> Contratado : Candidato acepta
    Oferta --> Descartado : Candidato rechaza oferta

    Preseleccionado --> Descartado : No encaja / No responde
    Contactado --> Descartado : No interesado
    EntrevistaInterna --> Descartado : No supera entrevista
    PropuestoAlCliente --> Descartado : Cliente no interesado
    EntrevistaCliente --> Descartado : No supera entrevista cliente

    Contratado --> [*]
    Descartado --> [*]
```

#### UC-21 — Mover candidato entre etapas

| Campo | Detalle |
|---|---|
| **Actor principal** | Recruiter |
| **Flujo principal** | 1. Recruiter arrastra la tarjeta del candidato (drag & drop) o usa el botón "Avanzar etapa" 2. Si hay campos requeridos en la etapa destino (ej.: resultado de entrevista), el sistema los solicita 3. Se registra automáticamente: etapa anterior, etapa nueva, usuario, timestamp 4. Se disparan comunicaciones automáticas configuradas para esa transición |
| **Flujo alternativo (descarte)** | Al mover a "Descartado", el sistema obliga a seleccionar motivo de la lista predefinida |
| **Postcondición** | Historial de etapas actualizado; candidato permanece en BD para futuros procesos |

---

## 6. Módulo: Pruebas y Evaluaciones

### Diagrama de casos de uso

```mermaid
flowchart LR
    REC([Recruiter])
    CAN([Candidato])
    EVAL([Plataforma de Evaluación])

    subgraph PR
        UC30[UC-30 Asignar prueba]
        UC31[UC-31 Enviar enlace prueba]
        UC32[UC-32 Completar prueba online]
        UC33[UC-33 Registrar resultado]
        UC34[UC-34 Configurar umbral]
        UC35[UC-35 Ver resultados comparativos]
    end

    REC --> UC30
    REC --> UC31
    REC --> UC34
    REC --> UC35
    CAN --> UC32
    UC31 --> CAN
    UC32 <--> EVAL
    UC33 --> EVAL
```

#### UC-30 — Asignar prueba a candidato

| Campo | Detalle |
|---|---|
| **Actor principal** | Recruiter |
| **Precondición** | Candidato en etapa "Entrevista Interna" o posterior |
| **Flujo principal** | 1. Recruiter selecciona tipo de prueba (técnica, psicométrica, idioma) 2. Selecciona plantilla o prueba de plataforma externa 3. Define fecha límite de realización 4. El sistema genera enlace único y envía email al candidato (UC-31) 5. El sistema monitoriza el estado: Pendiente / En curso / Completada |
| **Postcondición** | Resultado registrado automáticamente al completarse; notificación al recruiter |

#### UC-34 — Configurar umbral de paso automático

| Campo | Detalle |
|---|---|
| **Actor principal** | Manager / Administrador |
| **Flujo principal** | 1. Admin define puntuación mínima por tipo de prueba (ej.: ≥ 70%) 2. Si candidato supera umbral, avanza automáticamente a siguiente etapa 3. Si no lo supera, se descarta automáticamente con motivo "Prueba no superada" |

---

## 7. Módulo: Entrevistas

### Diagrama de casos de uso

```mermaid
flowchart LR
    REC([Recruiter])
    CAN([Candidato])
    HM([Hiring Manager])
    CAL([Google Calendar / Outlook])

    subgraph EN
        UC40[UC-40 Programar entrevista]
        UC41[UC-41 Enviar invitacion]
        UC42[UC-42 Enviar recordatorio]
        UC43[UC-43 Registrar resultado]
        UC44[UC-44 Reprogramar entrevista]
    end

    REC --> UC40
    REC --> UC43
    REC --> UC44
    HM --> UC43
    CAN --> UC44
    UC40 <--> CAL
    UC41 --> CAN
    UC41 --> HM
    UC42 --> CAN
    UC42 --> HM
```

#### UC-40 — Programar entrevista

| Campo | Detalle |
|---|---|
| **Actor principal** | Recruiter |
| **Precondición** | Candidato en etapa que requiere entrevista |
| **Flujo principal** | 1. Recruiter selecciona candidato y tipo de entrevista (presencial / telefónica / videoconferencia) 2. Ve disponibilidad del entrevistador via integración con calendario 3. Selecciona slot horario 4. Sistema crea evento en calendario y envía invitaciones automáticas (UC-41) 5. Se programan recordatorios automáticos 24h y 1h antes |
| **Postcondición** | Entrevista en calendario de recruiter, entrevistador y candidato |

#### UC-43 — Registrar resultado y feedback

| Campo | Detalle |
|---|---|
| **Actor principal** | Recruiter / Hiring Manager |
| **Flujo principal** | 1. Tras la entrevista, el entrevistador accede al formulario de evaluación 2. Puntúa competencias definidas (1-5) con campo de texto por competencia 3. Añade valoración global y recomendación (avanzar / descartar / dudoso) 4. Puede adjuntar archivo (notas, grabación) 5. El feedback queda visible para el equipo según permisos |

---

## 8. Módulo: Propuesta al Cliente

### Diagrama de casos de uso

```mermaid
flowchart LR
    REC([Recruiter])
    HM([Hiring Manager / Cliente])

    subgraph PC
        UC50[UC-50 Seleccionar terna]
        UC51[UC-51 Generar enlace seguro]
        UC52[UC-52 Revisar perfiles]
        UC53[UC-53 Dar feedback]
        UC54[UC-54 Recibir notificacion]
        UC55[UC-55 Registrar feedback]
    end

    REC --> UC50
    REC --> UC51
    REC --> UC54
    HM --> UC52
    HM --> UC53
    UC51 --> HM
    UC53 --> UC55
    UC55 --> REC
```

#### UC-51 — Generar portal / enlace seguro

| Campo | Detalle |
|---|---|
| **Actor principal** | Recruiter |
| **Flujo principal** | 1. Recruiter selecciona entre 2-5 candidatos del pipeline (estado "Propuesto al cliente") 2. Configura opciones: anonimizar datos hasta confirmación de interés (Sí/No), fecha de expiración del enlace 3. El sistema genera URL única con token 4. Recruiter envía el enlace al cliente por email o lo copia para compartir |
| **Postcondición** | Portal accesible por el cliente sin necesidad de login; acceso auditado |

#### UC-53 — Dar feedback por candidato

| Campo | Detalle |
|---|---|
| **Actor principal** | Hiring Manager (cliente externo) |
| **Flujo principal** | 1. Cliente abre el enlace del portal 2. Ve perfiles resumidos de los candidatos propuestos 3. Para cada candidato selecciona: Interesado / No interesado / Necesito más información 4. Opcionalmente añade comentario por candidato 5. Envía feedback |
| **Postcondición** | Feedback registrado automáticamente en el pipeline; notificación al recruiter (UC-54) |

---

## 9. Módulo: Comunicaciones

### Diagrama de casos de uso

```mermaid
flowchart LR
    REC([Recruiter])
    ADM([Administrador RRHH])
    CAN([Candidato])
    EMAIL([Sistema Email])

    subgraph COM
        UC60[UC-60 Configurar plantillas email]
        UC61[UC-61 Activar comunicaciones por etapa]
        UC62[UC-62 Envio automatico]
        UC63[UC-63 Envio manual]
        UC64[UC-64 Consultar log]
    end

    ADM --> UC60
    REC --> UC61
    REC --> UC63
    REC --> UC64
    UC62 --> EMAIL
    UC63 --> EMAIL
    EMAIL --> CAN
```

#### UC-60 — Configurar plantillas de email

| Campo | Detalle |
|---|---|
| **Actor principal** | Administrador RRHH |
| **Flujo principal** | 1. Admin accede al editor de plantillas 2. Selecciona etapa del pipeline para la que aplica 3. Redacta el cuerpo del email usando variables dinámicas: {{nombre_candidato}}, {{posicion}}, {{empresa_cliente}}, {{enlace_prueba}}, {{fecha_entrevista}}, etc. 4. Configura asunto, remitente y si el envío es automático o requiere confirmación |

#### UC-62 — Envío automático por transición de etapa

| Campo | Detalle |
|---|---|
| **Actor** | Sistema (trigger automático) |
| **Flujo principal** | 1. Se detecta cambio de etapa de un candidato 2. El sistema consulta si hay plantilla activa para esa transición 3. Renderiza la plantilla con los datos del candidato y la posición 4. Envía el email vía SMTP configurado 5. Registra en el log: timestamp, plantilla usada, estado de entrega |

---

## 10. Módulo: Reporting y Dashboard

### Diagrama de casos de uso

```mermaid
flowchart LR
    MGR([Manager])
    REC([Recruiter])
    ADM([Administrador RRHH])

    subgraph RPT
        UC70[UC-70 Ver dashboard]
        UC71[UC-71 Filtrar por recruiter fecha]
        UC72[UC-72 Ver embudo]
        UC73[UC-73 Generar informe]
        UC74[UC-74 Exportar PDF Excel]
        UC75[UC-75 Programar envio informe]
        UC76[UC-76 Configurar alertas]
    end

    MGR --> UC70
    MGR --> UC71
    MGR --> UC72
    MGR --> UC73
    MGR --> UC74
    MGR --> UC75
    MGR --> UC76
    REC --> UC70
    REC --> UC72
    ADM --> UC76
```

### Métricas del dashboard

```mermaid
graph LR
    DB((Dashboard))

    DB --> VEL[Velocidad]
    VEL --> V1[Time-to-shortlist]
    VEL --> V2[Time-to-hire]
    VEL --> V3[Dias promedio por etapa]

    DB --> CAL[Calidad]
    CAL --> C1[Match rate propuesta-aceptacion]
    CAL --> C2[Tasa de conversion por etapa]
    CAL --> C3[Candidatos reutilizados de BD]

    DB --> VOL[Volumen]
    VOL --> O1[Vacantes activas]
    VOL --> O2[Candidatos por etapa]
    VOL --> O3[Candidaturas por canal]

    DB --> EQ[Equipo]
    EQ --> E1[Posiciones por recruiter]
    EQ --> E2[Colocaciones cerradas mes]
    EQ --> E3[NPS recruiter]

    DB --> CLI[Cliente]
    CLI --> L1[Tiempo respuesta cliente]
    CLI --> L2[Feedback ratio]
    CLI --> L3[NPS cliente]
```

#### UC-70 — Ver dashboard en tiempo real

| Campo | Detalle |
|---|---|
| **Actor principal** | Manager / Recruiter |
| **Flujo principal** | 1. Usuario accede al dashboard 2. Ve: vacantes activas por estado, candidatos por etapa (funnel), alertas de procesos estancados (> N días sin movimiento), métricas del período actual vs. anterior 3. Puede hacer drill-down en cualquier métrica para ver el detalle |

---

## 11. Módulo: Administración del Sistema

### Diagrama de casos de uso

```mermaid
flowchart LR
    ADM([Administrador RRHH])
    MGR([Manager])

    subgraph ADMSYS
        UC80[UC-80 Gestionar usuarios]
        UC81[UC-81 Asignar roles]
        UC82[UC-82 Configurar pipeline]
        UC83[UC-83 Configurar motivos descarte]
        UC84[UC-84 Gestionar integraciones]
        UC85[UC-85 Configurar SSO]
        UC86[UC-86 Consultar auditoria]
        UC87[UC-87 Gestionar GDPR]
        UC88[UC-88 Derecho al olvido]
    end

    ADM --> UC80
    ADM --> UC81
    ADM --> UC82
    ADM --> UC83
    ADM --> UC84
    ADM --> UC85
    ADM --> UC86
    ADM --> UC87
    ADM --> UC88
    MGR --> UC82
    MGR --> UC86
```

#### UC-87 — Gestionar consentimientos GDPR

| Campo | Detalle |
|---|---|
| **Actor principal** | Administrador RRHH |
| **Flujo principal** | 1. Admin configura el texto del consentimiento por país/idioma 2. El sistema registra fecha, versión y canal de obtención del consentimiento de cada candidato 3. Admin puede consultar el estado de consentimiento de cualquier candidato 4. Candidatos sin consentimiento válido no pueden ser procesados |

#### UC-88 — Ejecutar derecho al olvido

| Campo | Detalle |
|---|---|
| **Actor principal** | Administrador RRHH |
| **Trigger** | Solicitud del candidato o expiración de período de retención |
| **Flujo principal** | 1. Admin busca candidato por email 2. El sistema muestra todos los datos y procesos asociados 3. Admin ejecuta anonimización: datos personales reemplazados por hash, CV eliminado, skills e historial de procesos conservados de forma anónima para estadísticas 4. Se genera registro de auditoría de la acción |

---

## 12. Flujo end-to-end del proceso de selección

Este diagrama muestra el flujo completo desde que el cliente solicita una posición hasta la contratación, integrando todos los módulos y actores.

```mermaid
sequenceDiagram
    actor Cliente as HiringManager
    actor REC as Recruiter
    actor CAN as Candidato
    participant SYS as RecruitFlow
    participant JB as JobBoards
    participant EMAIL as SistemaEmail
    participant EVAL as PlataformaEval

    rect rgb(230, 245, 255)
        Note over Cliente, SYS: FASE 1 — Apertura de posición
        Cliente ->> REC: Solicita perfil con requisitos
        REC ->> SYS: UC-01: Crear vacante con skills y pesos
        SYS -->> REC: Ranking de matching automático (UC-15)
        REC ->> SYS: UC-04: Publicar en canales seleccionados
        SYS ->> JB: Publicar oferta vía API
    end

    rect rgb(255, 245, 230)
        Note over CAN, SYS: FASE 2 — Recepción de candidaturas
        JB -->> SYS: Candidaturas entrantes por canal
        CAN ->> SYS: Aplica directamente (formulario web)
        SYS ->> SYS: UC-11: Parsear CV y mapear skills
        SYS ->> SYS: Deduplicación automática
        SYS -->> REC: Notificación: nuevo candidato en pipeline
    end

    rect rgb(240, 255, 240)
        Note over REC, CAN: FASE 3 — Revisión y contacto
        REC ->> SYS: UC-20: Añadir candidatos del ranking a la posición
        REC ->> SYS: UC-21: Mover candidatos a "Contactado"
        SYS ->> EMAIL: UC-62: Email automático al candidato
        EMAIL -->> CAN: "Tu candidatura ha sido revisada"
        CAN -->> REC: Respuesta positiva
        REC ->> SYS: UC-21: Mover a "Entrevista interna"
    end

    rect rgb(255, 240, 255)
        Note over REC, CAN: FASE 4 — Evaluación interna
        REC ->> SYS: UC-30: Asignar prueba online
        SYS ->> EMAIL: UC-31: Enviar enlace de prueba
        EMAIL -->> CAN: Email con enlace único
        CAN ->> EVAL: Completar prueba
        EVAL -->> SYS: UC-33: Resultado registrado
        SYS ->> SYS: UC-34: Verificar umbral de paso
        REC ->> SYS: UC-40: Programar entrevista interna
        SYS ->> EMAIL: UC-41: Invitaciones a candidato y entrevistador
        REC ->> SYS: UC-43: Registrar resultado de entrevista
    end

    rect rgb(255, 255, 230)
        Note over REC, Cliente: FASE 5 — Propuesta al cliente
        REC ->> SYS: UC-50: Seleccionar terna de candidatos
        SYS ->> SYS: UC-51: Generar enlace seguro del portal
        REC ->> Cliente: Enviar enlace del portal
        Cliente ->> SYS: UC-52: Revisar perfiles
        Cliente ->> SYS: UC-53: Dar feedback por candidato
        SYS -->> REC: UC-54: Notificación de feedback recibido
        SYS ->> SYS: UC-55: Registrar feedback en pipeline
    end

    rect rgb(240, 255, 255)
        Note over REC, Cliente: FASE 6 — Entrevista con cliente y oferta
        REC ->> SYS: UC-40: Programar entrevista con cliente
        SYS ->> EMAIL: Invitaciones a candidato y cliente
        Cliente ->> SYS: UC-43: Feedback post-entrevista
        REC ->> SYS: UC-21: Mover candidato a "Oferta"
        SYS ->> EMAIL: UC-62: Email automático al candidato
        EMAIL -->> CAN: "Tienes una oferta de empleo"
    end

    rect rgb(255, 240, 240)
        Note over REC, SYS: FASE 7 — Contratación y cierre
        CAN -->> REC: Acepta la oferta
        REC ->> SYS: UC-26: Registrar contratación
        SYS ->> EMAIL: Notificación a candidatos descartados
        SYS ->> SYS: UC-05: Cerrar vacante (estado: Cubierta)
        SYS -->> REC: Dashboard actualizado
    end
```

---

## Resumen de casos de uso por módulo

| Módulo | # UCs | Actores principales |
|---|---|---|
| Gestión de Vacantes | UC-01 a UC-07 | Recruiter, Admin |
| Base de Talento & Matching | UC-10 a UC-18 | Recruiter, Admin |
| Pipeline de Selección | UC-20 a UC-27 | Recruiter, Manager |
| Pruebas & Evaluaciones | UC-30 a UC-35 | Recruiter, Candidato |
| Gestión de Entrevistas | UC-40 a UC-44 | Recruiter, Candidato, Hiring Manager |
| Propuesta al Cliente | UC-50 a UC-55 | Recruiter, Hiring Manager |
| Comunicaciones | UC-60 a UC-64 | Admin, Recruiter, Candidato |
| Reporting & Dashboard | UC-70 a UC-76 | Manager, Recruiter |
| Administración del Sistema | UC-80 a UC-88 | Administrador RRHH |
| **Total** | **44 casos de uso** | |

---

*Documento generado a partir de ReadMe.md, PRD-RecruitFlow.md y recruitflow-vision-producto.md*
*Versión 1.0 — 2026-04-04*
