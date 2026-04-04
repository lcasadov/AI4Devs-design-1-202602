# RecruitFlow — Documento de Arquitectura
**Versión:** 1.0
**Fecha:** 2026-04-04
**Autor:** Arquitecto de Software Senior
**Estado:** Aprobado

---

## Tabla de contenidos

1. [Resumen ejecutivo](#1-resumen-ejecutivo)
2. [Stack tecnológico](#2-stack-tecnológico)
3. [Modelo C4](#3-modelo-c4)
4. [Arquitectura hexagonal — Backend](#4-arquitectura-hexagonal--backend)
5. [Arquitectura hexagonal — Frontend](#5-arquitectura-hexagonal--frontend)
6. [Estructura de módulos](#6-estructura-de-módulos)
7. [Diagramas de flujo clave](#7-diagramas-de-flujo-clave)
8. [Infraestructura y despliegue Docker](#8-infraestructura-y-despliegue-docker)
9. [Decisiones de arquitectura (ADR)](#9-decisiones-de-arquitectura-adr)

---

## 1. Resumen ejecutivo

RecruitFlow es una plataforma ATS (Applicant Tracking System) especializada para empresas de selección de personal. El sistema gestiona el ciclo completo de reclutamiento: desde la apertura de una vacante hasta la contratación del candidato, con un motor de matching automático basado en skills.

### Principios arquitectónicos

| Principio | Aplicación |
|---|---|
| **Arquitectura Hexagonal** | Separación estricta entre dominio, aplicación e infraestructura en Back y Front |
| **Modularidad** | Cada módulo de negocio es autónomo con sus propias capas hexagonales |
| **Domain-Driven Design** | El modelo de dominio dirige el diseño; la infraestructura se adapta al dominio |
| **AOP para cross-cutting** | Logging, auditoría, seguridad y métricas desacoplados mediante aspectos |
| **API-First** | El contrato REST se define antes del desarrollo; Front y Back evolucionan en paralelo |
| **Containerización** | Todo el sistema se despliega en Docker para portabilidad y escalabilidad |

---

## 2. Stack tecnológico

### Backend

| Componente | Tecnología | Versión | Propósito |
|---|---|---|---|
| Framework | Spring Boot | 3.3.x | Contenedor de aplicación y autoconfiguración |
| ORM | Hibernate + JPA | 6.x | Mapeo objeto-relacional y gestión de persistencia |
| AOP | Spring AOP + AspectJ | 6.x | Logging, auditoría, seguridad transversal |
| Seguridad | Spring Security + JWT | 6.x | Autenticación, autorización y OAuth2 |
| API | Spring Web MVC (REST) | 3.3.x | Exposición de endpoints REST |
| Validación | Bean Validation (Hibernate Validator) | 8.x | Validación de DTOs y entidades |
| Testing | JUnit 5 + Mockito + Testcontainers | — | Tests unitarios e integración |
| Build | Maven | 3.9.x | Gestión de dependencias y ciclo de vida |
| Documentación API | SpringDoc OpenAPI | 2.x | Generación automática de Swagger UI |

### Frontend

| Componente | Tecnología | Versión | Propósito |
|---|---|---|---|
| Framework | React | 18.x | Librería UI basada en componentes |
| Lenguaje | TypeScript | 5.x | Tipado estático sobre JavaScript |
| Build tool | Vite | 5.x | Bundler y servidor de desarrollo |
| Estado global | Zustand | 4.x | Gestión de estado ligero |
| HTTP client | Axios | 1.x | Comunicación con la API REST |
| Routing | React Router | 6.x | Navegación SPA |
| UI components | shadcn/ui + Tailwind CSS | — | Sistema de diseño |
| Testing | Vitest + Testing Library | — | Tests unitarios y de componentes |
| Forms | React Hook Form + Zod | — | Formularios con validación tipada |

### Infraestructura

| Componente | Tecnología | Propósito |
|---|---|---|
| Base de datos | SQL Server 2022 | Almacenamiento relacional principal |
| Contenedores | Docker + Docker Compose | Orquestación de servicios en desarrollo y producción |
| Proxy inverso | Nginx | Enrutamiento, SSL termination, serving de estáticos |
| Almacenamiento ficheros | Azure Blob Storage / S3 compatible | CVs, documentos, adjuntos |
| Email | SMTP / SendGrid | Comunicaciones con candidatos |
| CI/CD | GitHub Actions | Pipeline de integración y despliegue continuo |

---

## 3. Modelo C4

### 3.1 Nivel 1 — Contexto del sistema

Muestra RecruitFlow en relación con los usuarios y sistemas externos.

```mermaid
graph TB
    subgraph Usuarios
        REC([Recruiter])
        MGR([Manager])
        ADM([Admin RRHH])
        HM([Hiring Manager Cliente])
        CAN([Candidato])
    end

    subgraph RF[RecruitFlow]
        SYS[Sistema RecruitFlow\nATS para empresas de seleccion]
    end

    subgraph Externos
        JB[Job Boards\nLinkedIn Indeed InfoJobs]
        EMAIL[Servicio Email\nSendGrid SMTP]
        CAL[Calendario\nGoogle Outlook]
        EVAL[Plataforma Evaluacion\nTestGorilla HackerRank]
        HRIS[HRIS ERP\nSAP Workday]
        STORAGE[Almacenamiento\nAzure Blob S3]
    end

    REC --> SYS
    MGR --> SYS
    ADM --> SYS
    HM -->|Portal candidatos| SYS
    CAN -->|Email links| SYS

    SYS -->|Publica ofertas| JB
    SYS -->|Envia emails| EMAIL
    SYS -->|Crea eventos| CAL
    SYS -->|Asigna pruebas| EVAL
    SYS -->|Sincroniza empleados| HRIS
    SYS -->|Guarda ficheros| STORAGE

    style SYS fill:#1168BD,color:#fff
    style RF fill:#e8f4fd
```

---

### 3.2 Nivel 2 — Contenedores

Descomposición de RecruitFlow en contenedores Docker desplegables.

```mermaid
graph TB
    subgraph Clientes
        BR[Navegador Web\nReact SPA]
        EM[Cliente Email\nCandidato o Cliente]
    end

    subgraph DockerCompose[Docker Compose Network]
        NGX[Nginx\nProxy inverso\npuerto 80 443]
        FE[Frontend Container\nReact Vite\npuerto 3000]
        BE[Backend Container\nSpring Boot\npuerto 8080]
        DB[SQL Server Container\npuerto 1433]
    end

    subgraph ServiciosExternos
        BLOB[Azure Blob Storage]
        SMTP[SendGrid SMTP]
        LI[LinkedIn API]
    end

    BR -->|HTTPS| NGX
    NGX -->|Estaticos| FE
    NGX -->|API REST JSON| BE
    EM -->|Enlace portal propuesta| NGX

    BE -->|JDBC SQL Server Driver| DB
    BE -->|HTTP REST| BLOB
    BE -->|SMTP| SMTP
    BE -->|OAuth REST| LI

    style NGX fill:#009639,color:#fff
    style FE fill:#61DAFB,color:#000
    style BE fill:#6db33f,color:#fff
    style DB fill:#CC2927,color:#fff
```

---

### 3.3 Nivel 3 — Componentes del Backend

Componentes internos del contenedor Spring Boot organizados por capas hexagonales.

```mermaid
graph LR
    subgraph API[Adaptadores entrada REST]
        C1[VacantesController]
        C2[CandidatosController]
        C3[ApplicationController]
        C4[MatchingController]
        C5[PropuestaController]
        C6[AuthController]
    end

    subgraph APP[Capa de Aplicacion]
        UC1[GestionVacantesUseCase]
        UC2[GestionCandidatosUseCase]
        UC3[PipelineUseCase]
        UC4[MatchingEngineUseCase]
        UC5[PropuestaClienteUseCase]
        UC6[ComunicacionesUseCase]
    end

    subgraph DOM[Dominio]
        D1[Position Domain]
        D2[Candidate Domain]
        D3[Application Domain]
        D4[Matching Domain]
        D5[Skill Domain]
    end

    subgraph OUT[Adaptadores salida Persistencia]
        R1[PositionRepository JPA]
        R2[CandidateRepository JPA]
        R3[ApplicationRepository JPA]
        R4[SkillRepository JPA]
    end

    subgraph EXT[Adaptadores salida Externos]
        E1[EmailServiceAdapter]
        E2[JobBoardAdapter]
        E3[CalendarAdapter]
        E4[StorageAdapter]
    end

    subgraph AOP[Aspectos transversales]
        A1[LoggingAspect]
        A2[AuditAspect]
        A3[SecurityAspect]
        A4[PerformanceAspect]
    end

    API --> APP
    APP --> DOM
    DOM --> OUT
    DOM --> EXT
    AOP -.->|intercepta| API
    AOP -.->|intercepta| APP
```

---

### 3.4 Nivel 3 — Componentes del Frontend

Componentes internos del contenedor React organizados por capas hexagonales.

```mermaid
graph LR
    subgraph UI[Adaptadores entrada Componentes React]
        P1[VacantesPage]
        P2[CandidatosPage]
        P3[PipelinePage]
        P4[DashboardPage]
        P5[PropuestaPortalPage]
    end

    subgraph APPF[Capa Aplicacion Hooks y Estado]
        H1[useVacantes]
        H2[useCandidatos]
        H3[usePipeline]
        H4[useMatching]
        H5[useAuth]
        ST[Zustand Store]
    end

    subgraph DOMF[Dominio Tipos y Reglas]
        T1[Position Types]
        T2[Candidate Types]
        T3[Application Types]
        T4[Matching Types]
        T5[Validators]
    end

    subgraph APIC[Adaptadores salida API Clients]
        AC1[VacantesApiClient]
        AC2[CandidatosApiClient]
        AC3[PipelineApiClient]
        AC4[AuthApiClient]
    end

    UI --> APPF
    APPF --> DOMF
    APPF --> APIC
    APIC -->|Axios HTTP| BE[(Backend API)]
```

---

### 3.5 Nivel 4 — Código: Arquitectura hexagonal de un módulo

Detalle de implementación del módulo Candidatos como ejemplo canónico.

```mermaid
graph TB
    subgraph InboundAdapters[Adaptadores Entrada]
        REST[CandidateController\nREST @RestController]
        EVT[CVParserEventListener\n@EventListener]
    end

    subgraph InputPorts[Puertos Entrada]
        IP1[RegisterCandidateUseCase\ninterface]
        IP2[SearchCandidatesUseCase\ninterface]
        IP3[UpdateSkillsUseCase\ninterface]
    end

    subgraph ApplicationServices[Servicios de Aplicacion]
        AS1[CandidateApplicationService\nimplements UseCases]
    end

    subgraph Domain[Dominio]
        E1[Candidate\nAggregate Root]
        E2[CandidateSkill\nValue Object]
        E3[CandidateId\nValue Object]
        DS[CandidateMatchingService\nDomain Service]
        DE[CandidateRegisteredEvent\nDomain Event]
    end

    subgraph OutputPorts[Puertos Salida]
        OP1[CandidateRepository\ninterface]
        OP2[SkillCatalogPort\ninterface]
        OP3[CVParserPort\ninterface]
        OP4[NotificationPort\ninterface]
    end

    subgraph OutboundAdapters[Adaptadores Salida]
        JPA[CandidateJpaRepository\n@Repository]
        CVP[CVParserServiceAdapter\nIntegracion externa]
        NTF[EmailNotificationAdapter\nSendGrid]
        STO[BlobStorageAdapter\nAzure]
    end

    REST --> IP1
    REST --> IP2
    EVT --> IP3
    IP1 --> AS1
    IP2 --> AS1
    IP3 --> AS1
    AS1 --> E1
    AS1 --> DS
    E1 --> DE
    AS1 --> OP1
    AS1 --> OP2
    AS1 --> OP3
    AS1 --> OP4
    OP1 --> JPA
    OP3 --> CVP
    OP4 --> NTF
    STO --> AS1
```

---

## 4. Arquitectura hexagonal — Backend

### 4.1 Estructura de capas

La arquitectura hexagonal (Ports & Adapters) garantiza que el dominio no depende de ningún framework o tecnología externa.

```mermaid
graph TB
    subgraph INFRA[INFRAESTRUCTURA]
        subgraph IN[Adaptadores Entrada]
            RC[REST Controllers]
            EL[Event Listeners]
            SC[Scheduled Tasks]
        end
        subgraph OUT[Adaptadores Salida]
            JR[JPA Repositories]
            ES[Email Service]
            JB[JobBoard Client]
            BS[Blob Storage]
            CE[Calendar Client]
        end
    end

    subgraph APPLICATION[APLICACION]
        UC[Use Cases\nimplementaciones]
        DTO[DTOs Request Response]
        MP[Mappers]
    end

    subgraph DOMAIN[DOMINIO]
        AG[Aggregates]
        VO[Value Objects]
        DE[Domain Events]
        DS[Domain Services]
        PI[Ports In\ninterfaces Use Cases]
        PO[Ports Out\ninterfaces Repos y Services]
    end

    IN --> PI
    PI --> UC
    UC --> AG
    UC --> DS
    UC --> PO
    PO --> OUT
    DE -.->|publica| EL

    style DOMAIN fill:#fff3cd
    style APPLICATION fill:#d4edda
    style INFRA fill:#d1ecf1
```

### 4.2 Reglas de dependencia

```
INFRAESTRUCTURA → APLICACION → DOMINIO
                ↑                    |
                |____________________|
                (el dominio no conoce infraestructura)
```

- **Dominio**: cero dependencias externas. Solo Java puro.
- **Aplicación**: depende del dominio. Nunca de infraestructura.
- **Infraestructura**: depende de aplicación y dominio. Implementa los puertos.

### 4.3 Aspectos AOP

Los aspectos interceptan las capas de forma transversal sin modificar el código de negocio.

```mermaid
graph LR
    subgraph Aspectos
        LA[LoggingAspect\n@Around @Service]
        AA[AuditAspect\n@AfterReturning acciones criticas]
        SA[SecurityAspect\n@Before verificar permisos]
        PA[PerformanceAspect\n@Around medir tiempo]
        TA[TransactionAspect\n@Transactional]
    end

    subgraph Capas
        CT[Controllers]
        UC[Use Cases]
        RP[Repositories]
    end

    LA -.->|logging entrada salida| CT
    LA -.->|logging metodos| UC
    AA -.->|audit trail| UC
    SA -.->|check JWT roles| CT
    PA -.->|metricas latencia| UC
    PA -.->|metricas query| RP
    TA -.->|transacciones| UC
```

| Aspecto | Pointcut | Acción |
|---|---|---|
| `LoggingAspect` | `@Around execution(* ..service..*(..))`  | Log de entrada/salida con parámetros |
| `AuditAspect` | `@AfterReturning` en métodos `@Auditable` | Escribe en `AuditLog` con before/after |
| `SecurityAspect` | `@Before` en `@RequiresPermission` | Valida JWT y roles antes de ejecutar |
| `PerformanceAspect` | `@Around` en `@MonitorPerformance` | Mide tiempo y alerta si supera umbral |
| `TransactionAspect` | `@Transactional` en Use Cases | Gestión declarativa de transacciones |

### 4.4 Estructura de paquetes del proyecto

```
com.recruitflow
├── shared/                          # Compartido entre módulos
│   ├── domain/
│   │   ├── AggregateRoot.java
│   │   ├── DomainEvent.java
│   │   └── ValueObject.java
│   ├── infrastructure/
│   │   ├── aop/
│   │   │   ├── LoggingAspect.java
│   │   │   ├── AuditAspect.java
│   │   │   ├── SecurityAspect.java
│   │   │   └── PerformanceAspect.java
│   │   ├── config/
│   │   │   ├── SecurityConfig.java
│   │   │   ├── JpaConfig.java
│   │   │   └── OpenApiConfig.java
│   │   └── exception/
│   │       └── GlobalExceptionHandler.java
│   └── application/
│       └── dto/
│           ├── ApiResponse.java
│           └── PageResponse.java
│
├── vacantes/                        # Módulo Vacantes
│   ├── domain/
│   │   ├── model/
│   │   │   ├── Position.java        # Aggregate Root
│   │   │   ├── PositionId.java      # Value Object
│   │   │   └── PositionSkill.java   # Value Object
│   │   ├── service/
│   │   │   └── PositionDomainService.java
│   │   ├── event/
│   │   │   └── PositionCreatedEvent.java
│   │   └── port/
│   │       ├── in/
│   │       │   ├── CreatePositionUseCase.java
│   │       │   ├── UpdatePositionUseCase.java
│   │       │   └── PublishPositionUseCase.java
│   │       └── out/
│   │           ├── PositionRepository.java
│   │           └── JobBoardPort.java
│   ├── application/
│   │   ├── PositionApplicationService.java
│   │   ├── dto/
│   │   │   ├── CreatePositionRequest.java
│   │   │   └── PositionResponse.java
│   │   └── mapper/
│   │       └── PositionMapper.java
│   └── infrastructure/
│       ├── adapter/
│       │   ├── in/
│       │   │   └── web/
│       │   │       └── PositionController.java
│       │   └── out/
│       │       ├── persistence/
│       │       │   ├── PositionJpaRepository.java
│       │       │   └── PositionJpaEntity.java
│       │       └── jobboard/
│       │           └── LinkedInJobBoardAdapter.java
│       └── config/
│           └── VacantesModuleConfig.java
│
├── candidatos/                      # Módulo Candidatos (mismo patrón)
├── matching/                        # Módulo Motor de Matching
├── pipeline/                        # Módulo Pipeline / Candidaturas
├── entrevistas/                     # Módulo Entrevistas
├── pruebas/                         # Módulo Evaluaciones
├── propuestas/                      # Módulo Propuesta al Cliente
├── comunicaciones/                  # Módulo Comunicaciones
├── reporting/                       # Módulo Reporting
└── administracion/                  # Módulo Administración
```

---

## 5. Arquitectura hexagonal — Frontend

### 5.1 Principios aplicados al frontend

La arquitectura hexagonal en React separa:
- **Dominio**: tipos TypeScript, reglas de negocio puras, sin dependencias de React
- **Aplicación**: hooks de caso de uso, estado global con Zustand
- **Infraestructura**: componentes React (entrada), clientes API Axios (salida)

```mermaid
graph TB
    subgraph UIINFRA[INFRAESTRUCTURA UI]
        subgraph PAGES[Paginas y Componentes]
            PG[Pages\nVacantesPage PipelinePage]
            CM[Components\nPositionCard CandidateRow]
            LY[Layouts\nAppLayout AuthLayout]
        end
        subgraph APICLI[Clientes API]
            AC[ApiClient Axios]
            IC[Interceptors JWT refresh]
        end
    end

    subgraph APPF[APLICACION]
        HK[Custom Hooks\nuseVacantes usePipeline]
        ZS[Zustand Stores\npositionStore candidateStore]
        QR[React Query\ncache y sincronizacion]
    end

    subgraph DOMF[DOMINIO]
        TY[Types e Interfaces\nPosition Candidate Application]
        RU[Reglas de negocio\nvalidadores calculadores score]
        PT[Ports\ninterfaces de repositorio y servicio]
    end

    PAGES --> HK
    CM --> HK
    HK --> ZS
    HK --> QR
    HK --> TY
    HK --> RU
    QR --> AC
    AC --> IC
    AC -->|implementa| PT

    style DOMF fill:#fff3cd
    style APPF fill:#d4edda
    style UIINFRA fill:#d1ecf1
```

### 5.2 Estructura de directorios del proyecto React

```
src/
├── shared/                          # Compartido entre módulos
│   ├── domain/
│   │   ├── types/
│   │   │   ├── api.types.ts         # ApiResponse, PageResponse
│   │   │   └── auth.types.ts
│   │   └── utils/
│   │       ├── date.utils.ts
│   │       └── format.utils.ts
│   ├── application/
│   │   ├── useAuth.ts
│   │   └── useNotification.ts
│   └── infrastructure/
│       ├── api/
│       │   ├── axiosInstance.ts     # Config base + interceptores JWT
│       │   └── apiClient.ts
│       ├── components/
│       │   ├── AppLayout.tsx
│       │   ├── Sidebar.tsx
│       │   └── ui/                  # shadcn/ui components
│       └── router/
│           └── AppRouter.tsx
│
├── vacantes/                        # Módulo Vacantes
│   ├── domain/
│   │   ├── position.types.ts        # Position, PositionSkill, PositionStatus
│   │   ├── position.validators.ts   # Zod schemas
│   │   └── position.ports.ts        # IPositionRepository interface
│   ├── application/
│   │   ├── useVacantes.ts           # Hook: CRUD vacantes
│   │   ├── usePublicarVacante.ts    # Hook: publicación multi-canal
│   │   └── positionStore.ts         # Zustand slice
│   └── infrastructure/
│       ├── api/
│       │   └── vacantesApiClient.ts # Axios calls → implements port
│       ├── components/
│       │   ├── PositionForm.tsx
│       │   ├── PositionCard.tsx
│       │   └── SkillSelector.tsx
│       └── pages/
│           ├── VacantesListPage.tsx
│           └── VacanteDetailPage.tsx
│
├── candidatos/                      # Módulo Candidatos
├── matching/                        # Módulo Matching
├── pipeline/                        # Módulo Pipeline
├── entrevistas/                     # Módulo Entrevistas
├── propuestas/                      # Módulo Propuesta Cliente
├── comunicaciones/                  # Módulo Comunicaciones
├── reporting/                       # Módulo Reporting
└── administracion/                  # Módulo Admin
```

---

## 6. Estructura de módulos

### 6.1 Mapa de módulos y responsabilidades

```mermaid
graph TB
    subgraph CORE[Modulos Core]
        VA[Vacantes\nCRUD posiciones\nPublicacion multicanal]
        CA[Candidatos\nBase de talento\nParsing CV]
        MA[Matching Engine\nScoring algoritmo\nRanking automatico]
        PI[Pipeline\nGestion candidaturas\nEtapas configurables]
    end

    subgraph PROCESS[Modulos de Proceso]
        EN[Entrevistas\nAgenda calendario\nFeedback estructurado]
        PR[Pruebas\nAsignacion tests\nResultados automaticos]
        CP[Propuesta Cliente\nPortal seguro\nFeedback externo]
        CO[Comunicaciones\nTemplates email\nEnvio automatico]
    end

    subgraph SUPPORT[Modulos de Soporte]
        RP[Reporting\nDashboard metricas\nInformes automaticos]
        SK[Skills\nCatalogo maestro\nSinonimos]
        AD[Administracion\nUsuarios roles\nConfiguracion]
    end

    VA --> MA
    CA --> MA
    MA --> PI
    PI --> EN
    PI --> PR
    PI --> CP
    PI --> CO
    VA --> RP
    PI --> RP
    SK --> VA
    SK --> CA
    SK --> MA
    AD --> VA
    AD --> CA
```

### 6.2 Contratos de API REST por módulo

| Módulo | Prefijo | Operaciones principales |
|---|---|---|
| Vacantes | `/api/v1/positions` | GET, POST, PUT, DELETE, GET /match, POST /publish |
| Candidatos | `/api/v1/candidates` | GET, POST, PUT, POST /import, GET /{id}/skills |
| Matching | `/api/v1/matching` | GET /rank?positionId=, POST /recalculate |
| Pipeline | `/api/v1/applications` | GET, POST, PUT /stage, GET /history |
| Entrevistas | `/api/v1/interviews` | GET, POST, PUT, POST /feedback |
| Pruebas | `/api/v1/assessments` | GET, POST, GET /{id}/results |
| Propuestas | `/api/v1/proposals` | POST, GET /{token}/public, POST /{token}/feedback |
| Comunicaciones | `/api/v1/communications` | GET /templates, POST /send, GET /log |
| Reporting | `/api/v1/reports` | GET /dashboard, GET /funnel, GET /metrics |
| Administración | `/api/v1/admin` | CRUD users, roles, pipeline-templates, skills |

---

## 7. Diagramas de flujo clave

### 7.1 Flujo de autenticación y autorización (JWT + AOP)

```mermaid
sequenceDiagram
    participant FE as Frontend React
    participant NGX as Nginx
    participant BE as Spring Boot
    participant SA as SecurityAspect AOP
    participant DB as SQL Server

    FE ->> NGX: POST /api/v1/auth/login
    NGX ->> BE: Forward request
    BE ->> DB: Validate credentials
    DB -->> BE: User + roles
    BE -->> FE: JWT access token + refresh token

    Note over FE,BE: Peticion autenticada posterior

    FE ->> NGX: GET /api/v1/positions Authorization Bearer JWT
    NGX ->> BE: Forward con header
    BE ->> SA: @Before intercepta controlador
    SA ->> SA: Valida JWT firma y expiracion
    SA ->> SA: Verifica rol tiene permiso
    SA -->> BE: OK continua ejecucion
    BE ->> DB: Query posiciones
    DB -->> BE: Resultados
    BE -->> FE: 200 OK datos
```

### 7.2 Flujo del motor de matching (core del sistema)

```mermaid
flowchart TD
    A[Recruiter crea o modifica Position] --> B[PositionController POST PUT]
    B --> C[CreatePositionUseCase]
    C --> D[Position Aggregate guardado en BD]
    D --> E[PositionCreatedEvent publicado]
    E --> F[MatchingEngineEventListener]
    F --> G[MatchingEngineUseCase.calculateRanking]
    G --> H[Cargar todos los Candidates de la BD]
    H --> I{Candidate tiene todos los must-have skills?}
    I -->|No| J[Score = 0 excluido del ranking]
    I -->|Si| K[Calcular score nice-to-have ponderado por weight y nivel]
    K --> L[Ajuste por disponibilidad y pretension salarial]
    L --> M[Score final 0 a 100]
    M --> N[Persistir match_score y match_detail en Application]
    N --> O[Notificar Recruiter via Notification]
    J --> P[Ranking disponible en GET /matching/rank]
    O --> P
```

### 7.3 Flujo de propuesta al cliente

```mermaid
sequenceDiagram
    participant REC as Recruiter
    participant BE as Backend
    participant DB as SQL Server
    participant EMAIL as Email Service
    participant HM as Hiring Manager

    REC ->> BE: POST /proposals body candidateIds positionId
    BE ->> DB: Crear ClientProposal con token UUID
    BE ->> DB: Crear ClientProposalItems por candidato
    BE -->> REC: 201 Created url portal token

    REC ->> HM: Envia URL portal por email externo

    HM ->> BE: GET /proposals/token/public
    BE ->> DB: Validar token no expirado
    BE ->> DB: Registrar viewed_at
    BE -->> HM: Perfiles candidatos anonimizados

    HM ->> BE: POST /proposals/token/feedback body decisions
    BE ->> DB: Actualizar ClientProposalItem client_decision
    BE ->> DB: Crear Notification para Recruiter
    BE ->> EMAIL: Enviar email notificacion a Recruiter
    BE -->> HM: 200 OK feedback registrado

    REC ->> BE: GET /proposals/token/items
    BE -->> REC: Feedback del cliente por candidato
```

### 7.4 Flujo de parsing de CV y enriquecimiento de perfil

```mermaid
flowchart TD
    A[Recruiter sube CV PDF o Word] --> B[POST /candidates/import multipart]
    B --> C[CandidateController]
    C --> D[ImportCandidateUseCase]
    D --> E[CVParserPort.parse fichero]
    E --> F[CVParserServiceAdapter llamada externa]
    F --> G{Parsing exitoso?}
    G -->|No| H[Devuelve error con fichero original]
    G -->|Si| I[Extraer nombre email telefono experiencia formacion skills raw]
    I --> J[SkillNormalizationService]
    J --> K[Mapear skills raw a catalogo maestro via aliases]
    K --> L{Candidato ya existe por email o telefono?}
    L -->|Si| M[Merge propuesto al Recruiter]
    L -->|No| N[Crear nuevo Candidate]
    M --> O[Recruiter valida y confirma skills parseadas]
    N --> O
    O --> P[CandidateSkills guardadas con verified=false]
    P --> Q[Recruiter marca skills como verified=true]
    Q --> R[Candidate disponible en motor de matching]
```

---

## 8. Infraestructura y despliegue Docker

### 8.1 Arquitectura de contenedores

```mermaid
graph TB
    subgraph HOST[Servidor Host]
        subgraph DNET[Docker Network recruitflow-net]
            NGX[nginx\npuerto 80 443\nproxy inverso y SSL]
            FE[frontend\nNode nginx\npuerto 3000\nReact compilado]
            BE[backend\nJVM Spring Boot\npuerto 8080\nJAR ejecutable]
            SQL[sqlserver\nSQL Server 2022\npuerto 1433]
        end
        subgraph VOLS[Volumes]
            DBV[sqlserver-data\npersistencia BD]
            LGV[logs-volume\nlogs aplicacion]
        end
    end

    INET[Internet] -->|443| NGX
    NGX -->|3000 estaticos| FE
    NGX -->|8080 api| BE
    BE -->|1433 JDBC| SQL
    SQL --- DBV
    BE --- LGV
```

### 8.2 Docker Compose

```yaml
# docker-compose.yml
version: '3.9'

services:

  nginx:
    image: nginx:alpine
    container_name: recruitflow-nginx
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx/nginx.conf:/etc/nginx/nginx.conf:ro
      - ./nginx/certs:/etc/nginx/certs:ro
    depends_on:
      - frontend
      - backend
    networks:
      - recruitflow-net

  frontend:
    build:
      context: ./frontend
      dockerfile: Dockerfile
    container_name: recruitflow-frontend
    environment:
      - VITE_API_BASE_URL=http://backend:8080/api/v1
    depends_on:
      - backend
    networks:
      - recruitflow-net

  backend:
    build:
      context: ./backend
      dockerfile: Dockerfile
    container_name: recruitflow-backend
    environment:
      - SPRING_DATASOURCE_URL=jdbc:sqlserver://sqlserver:1433;databaseName=recruitflow
      - SPRING_DATASOURCE_USERNAME=${DB_USER}
      - SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
      - JWT_SECRET=${JWT_SECRET}
      - SENDGRID_API_KEY=${SENDGRID_API_KEY}
    depends_on:
      sqlserver:
        condition: service_healthy
    volumes:
      - logs-volume:/app/logs
    networks:
      - recruitflow-net

  sqlserver:
    image: mcr.microsoft.com/mssql/server:2022-latest
    container_name: recruitflow-sqlserver
    environment:
      - ACCEPT_EULA=Y
      - SA_PASSWORD=${DB_PASSWORD}
      - MSSQL_PID=Express
    volumes:
      - sqlserver-data:/var/opt/mssql
      - ./db/init:/docker-entrypoint-initdb.d:ro
    healthcheck:
      test: /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P ${DB_PASSWORD} -Q "SELECT 1"
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - recruitflow-net

networks:
  recruitflow-net:
    driver: bridge

volumes:
  sqlserver-data:
  logs-volume:
```

### 8.3 Dockerfile — Backend Spring Boot

```dockerfile
# backend/Dockerfile
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S recruitflow && adduser -S recruitflow -G recruitflow
COPY --from=builder /app/target/*.jar app.jar
USER recruitflow
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 8.4 Dockerfile — Frontend React

```dockerfile
# frontend/Dockerfile
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json .
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
EXPOSE 3000
```

### 8.5 Pipeline CI/CD

```mermaid
flowchart LR
    A[git push main] --> B[GitHub Actions trigger]
    B --> C[Test Backend\nmvn test]
    B --> D[Test Frontend\nnpm run test]
    C --> E{Tests OK?}
    D --> E
    E -->|No| F[Notificacion fallo al equipo]
    E -->|Si| G[Build Docker images\ndocker build push registry]
    G --> H[Deploy staging\ndocker compose pull up]
    H --> I[Smoke tests E2E]
    I --> J{OK?}
    J -->|No| K[Rollback version anterior]
    J -->|Si| L[Aprobar deploy produccion]
    L --> M[Deploy produccion\ndocker compose up produccion]
```

---

## 9. Decisiones de arquitectura (ADR)

### ADR-001 — Arquitectura Hexagonal sobre Layered Architecture

| Campo | Detalle |
|---|---|
| **Estado** | Aprobado |
| **Contexto** | El sistema tiene múltiples integraciones externas (job boards, email, calendarios, evaluaciones) que pueden cambiar con el tiempo |
| **Decisión** | Usar arquitectura hexagonal (Ports & Adapters) en lugar de arquitectura en capas tradicional |
| **Consecuencias positivas** | El dominio es independiente de frameworks; los adaptadores se pueden sustituir sin tocar el dominio; facilita el testing con mocks de puertos |
| **Consecuencias negativas** | Mayor complejidad inicial; más archivos e interfaces; curva de aprendizaje para el equipo |

### ADR-002 — Spring AOP para cross-cutting concerns

| Campo | Detalle |
|---|---|
| **Estado** | Aprobado |
| **Contexto** | Logging, auditoría GDPR y métricas de rendimiento deben aplicarse a múltiples módulos sin contaminar el código de negocio |
| **Decisión** | Implementar logging, auditoría, seguridad y métricas como aspectos Spring AOP con anotaciones personalizadas |
| **Consecuencias positivas** | El código de dominio queda limpio; los aspectos se activan/desactivan sin cambios en el dominio |
| **Consecuencias negativas** | Comportamiento no evidente al leer el código; debugging más complejo; proxy-based AOP no intercepta llamadas internas |

### ADR-003 — SQL Server como base de datos principal

| Campo | Detalle |
|---|---|
| **Estado** | Aprobado |
| **Contexto** | Necesitamos ACID, joins complejos para matching, full-text search para búsqueda de candidatos y JSON support para match_detail |
| **Decisión** | SQL Server 2022 como base de datos principal |
| **Consecuencias positivas** | JSON support nativo para match_detail; Full-Text Search para búsqueda; compatibilidad con entorno corporativo cliente |
| **Consecuencias negativas** | Licencia de coste; mayor consumo de recursos que alternativas open-source |

### ADR-004 — Modularización por capacidad de negocio

| Campo | Detalle |
|---|---|
| **Estado** | Aprobado |
| **Contexto** | El sistema tiene 9 módulos funcionales claramente diferenciados; necesitamos que puedan evolucionar de forma independiente |
| **Decisión** | Cada módulo de negocio contiene sus propias capas hexagonales completas (domain, application, infrastructure) |
| **Consecuencias positivas** | Alta cohesión dentro del módulo; bajo acoplamiento entre módulos; facilita migración futura a microservicios si fuese necesario |
| **Consecuencias negativas** | Duplicación de algún boilerplate entre módulos; gestión de transacciones entre módulos requiere cuidado |

### ADR-005 — Zustand sobre Redux para estado frontend

| Campo | Detalle |
|---|---|
| **Estado** | Aprobado |
| **Contexto** | El frontend necesita gestión de estado global para candidatos en pipeline, posiciones activas y notificaciones |
| **Decisión** | Zustand como gestor de estado global; React Query para estado del servidor (caché de llamadas API) |
| **Consecuencias positivas** | API minimalista; menos boilerplate que Redux; React Query optimiza automáticamente la caché y refetch |
| **Consecuencias negativas** | Menor ecosistema que Redux; DevTools menos maduras |

### ADR-006 — Comunicación Front-Back exclusivamente via API REST

| Campo | Detalle |
|---|---|
| **Estado** | Aprobado |
| **Contexto** | El frontend y backend deben poder desarrollarse y desplegarse de forma independiente |
| **Decisión** | El frontend solo se comunica con el backend a través de la API REST documentada con OpenAPI. No hay SSR ni BFF. Para notificaciones en tiempo real se usará polling en v1 y WebSocket en v2. |
| **Consecuencias positivas** | Desacoplamiento total; API reutilizable para futuros clientes mobile o integraciones |
| **Consecuencias negativas** | Latencia de polling para notificaciones en v1; CORS debe configurarse correctamente |

---

*Documento de arquitectura RecruitFlow v1.0 — Sujeto a revisión en cada sprint de arquitectura*
*Próxima revisión: 2026-05-04*
