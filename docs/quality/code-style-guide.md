# RecruitFlow — Guía de Estilo de Código
**Versión:** 1.0
**Fecha:** 2026-04-04
**Autor:** Tech Lead
**Estado:** Aprobado — cumplimiento obligatorio

---

## Tabla de contenidos

1. [Principios generales](#1-principios-generales)
2. [Backend Java — Google Style + OWASP](#2-backend-java--google-style--owasp)
3. [Frontend TypeScript — Google Style + ESLint](#3-frontend-typescript--google-style--eslint)
4. [Convenciones de naming](#4-convenciones-de-naming)
5. [Seguridad en código (OWASP)](#5-seguridad-en-código-owasp)
6. [Checkstyle — Configuración backend](#6-checkstyle--configuración-backend)
7. [ESLint + Prettier — Configuración frontend](#7-eslint--prettier--configuración-frontend)
8. [Git y commits](#8-git-y-commits)
9. [Revisión de código (Code Review)](#9-revisión-de-código-code-review)

---

## 1. Principios generales

Estas reglas aplican a todo el código de RecruitFlow independientemente de la capa o tecnología.

| Principio | Descripción |
|---|---|
| **Legibilidad primero** | El código se lee muchas más veces de las que se escribe. Optimiza para el lector. |
| **Nombres que explican intención** | Un nombre claro elimina la necesidad de un comentario |
| **Funciones pequeñas** | Una función hace una sola cosa. Si necesita un comentario para explicar qué hace, es demasiado grande. |
| **Sin magia** | No usar números o strings literales; siempre constantes con nombre |
| **Seguridad por defecto** | Denegar es más seguro que permitir. Validar siempre en la frontera del sistema. |
| **Falla de forma obvia** | Los errores deben ser explícitos. Nunca silenciar excepciones. |
| **No repetirse (DRY)** | Pero no abstraer prematuramente. Tres repeticiones justifican una abstracción. |

### Lo que los linters NO pueden detectar — revisión manual obligatoria

- Lógica de negocio incorrecta
- Decisiones de arquitectura pobres
- Vulnerabilidades de seguridad complejas (IDOR, race conditions)
- Nombres que mienten sobre la intención
- Tests que no prueban nada real

---

## 2. Backend Java — Google Style + OWASP

Basado en [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) con extensiones de seguridad.

### 2.1 Formato general

```java
// ✅ CORRECTO — indentación de 2 espacios (Google Style)
public class MatchingService {

  private final CandidateRepository candidateRepository;
  private final MatchScoreCalculator scoreCalculator;

  public MatchingResult runMatching(UUID positionId, UUID companyId, RunMatchingRequest request) {
    Position position = positionRepository
        .findByIdAndCompanyId(positionId, companyId)
        .orElseThrow(() -> new ResourceNotFoundException("Position not found: " + positionId));

    return executeMatching(position, request);
  }
}

// ❌ INCORRECTO — indentación de 4 espacios, llaves en línea nueva
public class MatchingService
{
    private final CandidateRepository candidateRepository;

    public MatchingResult runMatching(UUID positionId, UUID companyId, RunMatchingRequest request)
    {
        // ...
    }
}
```

### 2.2 Longitud de línea y saltos

```java
// Máximo 100 caracteres por línea

// ✅ CORRECTO — salto de línea antes del operador en encadenamiento
List<MatchResult> results = candidates.stream()
    .filter(c -> c.getStatus() == CandidateStatus.ACTIVE)
    .map(c -> scoreCalculator.calculate(c, position))
    .filter(r -> r.getScore() >= request.getMinScore())
    .sorted(Comparator.comparingDouble(MatchResult::getScore).reversed())
    .limit(request.getLimit())
    .toList();

// ✅ CORRECTO — parámetros largos con indentación de 4 espacios adicionales
public MatchingResult runMatching(
    UUID positionId,
    UUID companyId,
    RunMatchingRequest request) {
  // ...
}
```

### 2.3 Imports

```java
// Orden obligatorio (separados por línea en blanco):
// 1. java.*
// 2. javax.* / jakarta.*
// 3. org.*
// 4. com.* (externos)
// 5. com.recruitflow.* (internos)

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.recruitflow.shared.exception.ResourceNotFoundException;
import com.recruitflow.vacantes.domain.model.Position;

// ❌ PROHIBIDO — wildcard imports
import java.util.*;
import com.recruitflow.vacantes.*;
```

### 2.4 Clases y estructuras

```java
// Orden de miembros dentro de una clase:
// 1. Constantes estáticas
// 2. Variables de instancia (final primero)
// 3. Constructores
// 4. Métodos públicos
// 5. Métodos protected/package-private
// 6. Métodos privados
// 7. Clases internas / enums

@Service
@Transactional(readOnly = true)
public class CandidateApplicationService {

  // 1. Constantes
  private static final int MAX_SKILLS_PER_CANDIDATE = 50;
  private static final Logger log = LoggerFactory.getLogger(CandidateApplicationService.class);

  // 2. Variables de instancia — final primero
  private final CandidateRepository candidateRepository;
  private final DomainEventPublisher eventPublisher;

  // 3. Constructor — siempre inyección por constructor (nunca @Autowired en campo)
  public CandidateApplicationService(
      CandidateRepository candidateRepository,
      DomainEventPublisher eventPublisher) {
    this.candidateRepository = candidateRepository;
    this.eventPublisher = eventPublisher;
  }

  // 4. Métodos públicos
  public Candidate findById(UUID id, UUID companyId) {
    return candidateRepository.findByIdAndCompanyId(id, companyId)
        .orElseThrow(() -> new ResourceNotFoundException("Candidate not found: " + id));
  }

  // 6. Métodos privados
  private void validateSkillLimit(List<CandidateSkill> skills) {
    if (skills.size() > MAX_SKILLS_PER_CANDIDATE) {
      throw new BusinessRuleException("Cannot exceed " + MAX_SKILLS_PER_CANDIDATE + " skills");
    }
  }
}
```

### 2.5 Comentarios y Javadoc

```java
// ✅ CORRECTO — Javadoc solo en API pública de interfaces y clases de dominio
/**
 * Calcula el score de compatibilidad entre un candidato y una vacante.
 *
 * @param candidate el candidato a evaluar
 * @param position  la vacante contra la que se evalúa
 * @return score entre 0.0 (incompatible) y 1.0 (compatibilidad perfecta)
 * @throws IllegalArgumentException si el candidato no tiene skills registradas
 */
float calculate(Candidate candidate, Position position);

// ✅ CORRECTO — comentario inline solo cuando la lógica no es autoevidente
// Fórmula: peso skill (70%) + experiencia (20%) + ubicación (10%)
float finalScore = (skillScore * 0.7f) + (experienceScore * 0.2f) + (locationScore * 0.1f);

// ❌ INCORRECTO — comentario que repite lo que hace el código
// Obtener el candidato por id
Candidate candidate = candidateRepository.findById(id);

// ❌ PROHIBIDO — código comentado sin justificación
// candidateRepository.delete(candidate);
// TODO: revisar esto (sin issue asociado)
```

### 2.6 Manejo de excepciones

```java
// ✅ CORRECTO — excepciones específicas, nunca silenciadas
try {
  return cvParserClient.parse(fileContent);
} catch (CvParserTimeoutException e) {
  log.warn("CV parser timeout for file: {}, retrying once", fileName);
  return cvParserClient.parse(fileContent);  // un solo retry
} catch (CvParserException e) {
  log.error("CV parsing failed for file: {}", fileName, e);
  throw new ExternalServiceException("CV parsing service unavailable", e);
}

// ❌ INCORRECTO — capturar Exception genérica
try {
  return cvParserClient.parse(fileContent);
} catch (Exception e) {
  log.error("Error", e);
  return null;  // NUNCA devolver null en caso de error
}

// ❌ PROHIBIDO — silenciar excepciones
try {
  auditLog.save(entry);
} catch (Exception e) {
  // ignorar
}
```

---

## 3. Frontend TypeScript — Google Style + ESLint

### 3.1 Formato general

```typescript
// ✅ CORRECTO — 2 espacios de indentación, punto y coma siempre
const usePositions = (filters?: PositionFilters): UseQueryResult<PaginatedResponse<Position>> => {
  return useQuery({
    queryKey: ['positions', filters],
    queryFn: () => positionApi.list(filters),
    staleTime: 5 * 60 * 1000,
  });
};

// ✅ CORRECTO — línea en blanco entre bloques lógicos
const PositionCard = ({ position, onEdit, onClose }: PositionCardProps) => {
  const { role } = useAuthStore();
  const isAdmin = role === 'ADMIN';

  const handleEdit = () => {
    onEdit(position.id);
  };

  return (
    <Card>
      <CardHeader title={position.title} />
      <CardBody>{position.clientName}</CardBody>
      {isAdmin && <Button onClick={handleClose}>Cerrar vacante</Button>}
    </Card>
  );
};
```

### 3.2 Tipos y TypeScript estricto

```typescript
// ✅ CORRECTO — tipos explícitos en APIs públicas
interface PositionCardProps {
  position: Position;
  onEdit: (id: string) => void;
  onClose?: (id: string) => void;
}

// ✅ CORRECTO — tipos de retorno explícitos en funciones exportadas
export const formatSalary = (min: number, max: number, currency: string): string => {
  return `${min.toLocaleString()} – ${max.toLocaleString()} ${currency}`;
};

// ✅ CORRECTO — usar type alias para uniones y primitivos con significado
type PositionStatus = 'DRAFT' | 'OPEN' | 'ON_HOLD' | 'CLOSED';
type CompanyId = string;  // alias semántico

// ❌ PROHIBIDO — any
const processData = (data: any) => { ... };        // usar unknown o tipo específico
const ref = useRef<any>(null);                      // usar el tipo concreto

// ❌ PROHIBIDO — non-null assertion sin justificación
const id = user!.id;          // usar optional chaining + guard
const id = user?.id ?? '';    // ✅ correcto
```

### 3.3 Componentes React

```tsx
// ✅ CORRECTO — componentes funcionales con tipos explícitos
// Siempre named export (no default export en componentes)

export const PositionList = ({ companyId }: PositionListProps): JSX.Element => {
  const { data, isLoading, isError } = usePositions({ companyId });

  if (isLoading) return <LoadingSpinner />;
  if (isError)   return <ErrorMessage message="No se pudieron cargar las vacantes" />;
  if (!data?.content.length) return <EmptyState message="No hay vacantes activas" />;

  return (
    <ul role="list" aria-label="Lista de vacantes">
      {data.content.map((position) => (
        <li key={position.id}>
          <PositionCard position={position} />
        </li>
      ))}
    </ul>
  );
};

// ❌ INCORRECTO — default export, sin tipos, lógica mezclada con renderizado
export default function PositionList(props) {
  const [positions, setPositions] = useState([]);
  useEffect(() => {
    fetch('/api/positions').then(r => r.json()).then(setPositions);
  }, []);
  return <div>{positions.map(p => <div>{p.title}</div>)}</div>;
}
```

### 3.4 Hooks personalizados

```typescript
// ✅ CORRECTO — un hook, una responsabilidad
// Nombre: use + verbo/sustantivo que describe lo que hace

export const useCreatePosition = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (request: CreatePositionRequest) => positionApi.create(request),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['positions'] });
      toast.success('Vacante creada correctamente');
    },
    onError: (error: ApiError) => {
      toast.error(error.message ?? 'Error al crear la vacante');
    },
  });
};

// ✅ CORRECTO — separar lógica de estado del componente
export const usePositionFilters = () => {
  const [filters, setFilters] = useState<PositionFilters>({});

  const updateStatus  = (status: PositionStatus) => setFilters(f => ({ ...f, status }));
  const updateSearch  = (search: string)         => setFilters(f => ({ ...f, search }));
  const clearFilters  = ()                        => setFilters({});

  return { filters, updateStatus, updateSearch, clearFilters };
};
```

---

## 4. Convenciones de naming

### 4.1 Backend — Java

| Elemento | Convención | Ejemplo |
|---|---|---|
| **Clases** | PascalCase | `CandidateApplicationService` |
| **Interfaces** | PascalCase sin prefijo I | `CandidateRepository` (no `ICandidateRepository`) |
| **Enums** | PascalCase | `ApplicationStage` |
| **Enum valores** | UPPER_SNAKE_CASE | `APPLIED`, `SCREENING`, `INTERVIEW` |
| **Métodos** | camelCase · verbo + sustantivo | `findByEmailAndCompanyId`, `runMatching`, `advanceStageTo` |
| **Variables** | camelCase | `matchScore`, `companyId` |
| **Constantes** | UPPER_SNAKE_CASE | `MAX_SKILLS_PER_CANDIDATE`, `DEFAULT_PAGE_SIZE` |
| **Paquetes** | lowercase · singular | `com.recruitflow.candidatos.domain.model` |
| **Tests** | `ClassNameTest` / `ClassNameIT` | `MatchingServiceTest`, `CandidateApiIT` |

#### Naming por capa — Backend

| Capa | Sufijo | Ejemplo |
|---|---|---|
| Dominio — entidad | *(sin sufijo)* | `Candidate`, `Position`, `Application` |
| Dominio — value object | *(sin sufijo)* | `Email`, `MatchScore`, `Salary` |
| Dominio — evento | `Event` | `CandidateCreatedEvent`, `StageChangedEvent` |
| Dominio — excepción | `Exception` | `InvalidStageTransitionException` |
| Dominio — port (input) | `UseCase` | `CreateCandidateUseCase`, `RunMatchingUseCase` |
| Dominio — port (output) | `Repository` / `Port` | `CandidateRepository`, `CvParserPort` |
| Aplicación — use case | `Service` | `CandidateApplicationService` |
| Aplicación — DTO entrada | `Request` / `Command` | `CreateCandidateRequest`, `AdvanceStageCommand` |
| Aplicación — DTO salida | `Response` / `Dto` | `CandidateResponse`, `MatchingResultDto` |
| Infraestructura — JPA | `Entity` | `CandidateEntity`, `PositionEntity` |
| Infraestructura — JPA repo | `JpaRepository` | `CandidateJpaRepository` |
| Infraestructura — adapter | `Adapter` | `CvParserAdapter`, `EmailSenderAdapter` |
| Infraestructura — mapper | `Mapper` | `CandidateMapper`, `PositionMapper` |
| Controller | `Controller` | `CandidateController`, `MatchingController` |
| Aspecto AOP | `Aspect` | `LoggingAspect`, `AuditAspect` |
| Configuración Spring | `Config` | `SecurityConfig`, `JpaConfig` |

#### Naming de métodos — verbos por capa

| Capa | Verbo | Ejemplo |
|---|---|---|
| Repository | `find`, `save`, `delete`, `exists` | `findByIdAndCompanyId`, `save`, `existsByEmail` |
| Use Case / Service | verbo de negocio | `create`, `update`, `advanceStageTo`, `runMatching`, `discard` |
| Controller | HTTP verb implícito | `createPosition`, `getPosition`, `updatePosition` |

```java
// ✅ CORRECTO — verbo de negocio en service
candidateService.create(command);
applicationService.advanceStageTo(appId, INTERVIEW);
matchingService.runMatching(positionId, request);

// ❌ INCORRECTO — verbo genérico en service
candidateService.save(candidate);
applicationService.update(appId, stage);
matchingService.doMatching(positionId);
```

---

### 4.2 Frontend — TypeScript / React

| Elemento | Convención | Ejemplo |
|---|---|---|
| **Componentes React** | PascalCase | `PositionCard`, `KanbanBoard`, `CandidateForm` |
| **Hooks** | `use` + PascalCase | `usePositions`, `useCreateCandidate`, `usePipelineStore` |
| **Funciones** | camelCase · verbo + sustantivo | `formatSalary`, `calculateMatchColor`, `buildPositionQuery` |
| **Variables** | camelCase | `positionId`, `matchScore`, `isLoading` |
| **Constantes** | UPPER_SNAKE_CASE | `MAX_FILE_SIZE_MB`, `DEFAULT_PAGE_SIZE` |
| **Tipos / Interfaces** | PascalCase | `Position`, `CreatePositionRequest`, `PositionCardProps` |
| **Enums** | PascalCase · valores UPPER_SNAKE_CASE | `PositionStatus.OPEN` |
| **Archivos componente** | PascalCase + `.tsx` | `PositionCard.tsx` |
| **Archivos hook** | camelCase + `.ts` | `usePositions.ts` |
| **Archivos utilidad** | camelCase + `.ts` | `formatters.ts`, `validators.ts` |
| **Archivos test** | `NombreOriginal.test.tsx` | `PositionCard.test.tsx` |
| **Archivos E2E** | `flujo.cy.ts` | `create-position.cy.ts` |

#### Naming de props en componentes

```tsx
// ✅ CORRECTO — callbacks con prefijo "on"
interface PositionCardProps {
  position: Position;
  onEdit:   (id: string) => void;   // handler externo: on + verbo
  onClose:  (id: string) => void;
  isLoading?: boolean;              // booleanos con prefijo is/has/can/should
  hasError?:  boolean;
  canEdit?:   boolean;
}

// ✅ CORRECTO — handlers internos con prefijo "handle"
const PositionCard = ({ position, onEdit }: PositionCardProps) => {
  const handleEditClick = () => onEdit(position.id);   // handler interno: handle + acción

  return <Button onClick={handleEditClick}>Editar</Button>;
};
```

---

### 4.3 Base de datos — SQL Server

| Elemento | Convención | Ejemplo |
|---|---|---|
| **Tablas** | snake_case · plural | `positions`, `candidates`, `stage_history` |
| **Columnas** | snake_case | `company_id`, `match_score`, `created_at` |
| **Claves primarias** | `id` | `id UUID NOT NULL` |
| **Claves foráneas** | `{tabla_referenciada_singular}_id` | `position_id`, `candidate_id` |
| **Índices** | `idx_{tabla}_{columnas}` | `idx_candidates_company_id`, `idx_applications_position_stage` |
| **Unique constraints** | `uq_{tabla}_{columnas}` | `uq_candidates_email_company` |
| **Foreign keys** | `fk_{tabla}_{referencia}` | `fk_applications_position`, `fk_applications_candidate` |
| **Migraciones Flyway** | `V{version}__{descripcion}.sql` | `V001__create_positions_table.sql` |

```sql
-- ✅ CORRECTO
CREATE TABLE candidates (
    id              UNIQUEIDENTIFIER NOT NULL DEFAULT NEWSEQUENTIALID(),
    company_id      UNIQUEIDENTIFIER NOT NULL,
    first_name      NVARCHAR(100)    NOT NULL,
    last_name       NVARCHAR(100)    NOT NULL,
    email           NVARCHAR(255)    NOT NULL,
    status          NVARCHAR(20)     NOT NULL DEFAULT 'ACTIVE',
    created_at      DATETIME2        NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT pk_candidates          PRIMARY KEY (id),
    CONSTRAINT fk_candidates_company  FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT uq_candidates_email_company UNIQUE (email, company_id),
    CONSTRAINT chk_candidates_status  CHECK (status IN ('ACTIVE','INACTIVE','BLACKLISTED'))
);

-- ❌ INCORRECTO
CREATE TABLE Candidate (
    CandidateId  INT          IDENTITY,
    CompanyId    INT,
    FirstName    VARCHAR(100),
    EMail        VARCHAR(255)
);
```

---

## 5. Seguridad en código (OWASP)

### 5.1 OWASP Top 10 — Reglas obligatorias

#### A01 — Broken Access Control

```java
// ✅ CORRECTO — verificar siempre company_id en queries
public Candidate findById(UUID id, UUID companyId) {
  // Siempre filtrar por companyId extraído del JWT, nunca confiar en el path
  return repo.findByIdAndCompanyId(id, companyId)
      .orElseThrow(() -> new ResourceNotFoundException("Candidate not found: " + id));
  // 404 en lugar de 403 para no revelar existencia de recursos de otros tenants
}

// ❌ INCORRECTO — buscar solo por id sin tenant
public Candidate findById(UUID id) {
  return repo.findById(id).orElseThrow(...);  // IDOR vulnerability
}
```

#### A02 — Cryptographic Failures

```java
// ✅ CORRECTO — BCrypt con cost factor >= 12
@Bean
public PasswordEncoder passwordEncoder() {
  return new BCryptPasswordEncoder(12);
}

// ✅ CORRECTO — tokens opacos hasheados con SHA-256 antes de almacenar
public void storeRefreshToken(String rawToken, UUID userId) {
  String hashed = DigestUtils.sha256Hex(rawToken);
  refreshTokenRepository.save(new RefreshToken(hashed, userId, Instant.now().plus(7, DAYS)));
}

// ❌ PROHIBIDO — MD5, SHA-1 para contraseñas
MessageDigest.getInstance("MD5").digest(password.getBytes());

// ❌ PROHIBIDO — almacenar tokens en claro en BD
refreshTokenRepository.save(new RefreshToken(rawToken, userId));
```

#### A03 — Injection

```java
// ✅ CORRECTO — JPQL con parámetros nombrados
@Query("SELECT c FROM CandidateEntity c WHERE c.companyId = :companyId AND c.status = :status")
List<CandidateEntity> findByCompanyAndStatus(
    @Param("companyId") UUID companyId,
    @Param("status") CandidateStatus status);

// ✅ CORRECTO — Criteria API para queries dinámicas
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<CandidateEntity> cq = cb.createQuery(CandidateEntity.class);
Root<CandidateEntity> root = cq.from(CandidateEntity.class);
cq.where(cb.equal(root.get("companyId"), companyId));  // parametrizado

// ❌ PROHIBIDO — concatenación de strings en queries
String query = "SELECT * FROM candidates WHERE email = '" + email + "'";  // SQL injection
em.createNativeQuery(query);
```

```typescript
// ✅ CORRECTO — sanitizar antes de insertar en DOM
import DOMPurify from 'dompurify';
const safeHtml = DOMPurify.sanitize(userContent);
element.innerHTML = safeHtml;

// ❌ PROHIBIDO — innerHTML con datos del usuario sin sanitizar
element.innerHTML = userInput;  // XSS vulnerability
```

#### A04 — Insecure Design

```java
// ✅ CORRECTO — límite de intentos de login
@RateLimit(requestsPerMinute = 5, key = "ip")
@PostMapping("/auth/login")
public AuthResponse login(@Valid @RequestBody LoginRequest request) { ... }

// ✅ CORRECTO — no exponer stack traces en respuestas de error
@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse> handleUnexpected(Exception e) {
  log.error("Unexpected error", e);  // log interno completo
  return ResponseEntity.status(500)
      .body(new ErrorResponse("INTERNAL_ERROR", "Ha ocurrido un error inesperado"));
  // Sin e.getMessage() ni stack trace en la respuesta
}
```

#### A05 — Security Misconfiguration

```java
// ✅ CORRECTO — deshabilitar endpoints de actuator no necesarios en producción
management:
  endpoints:
    web:
      exposure:
        include: health, info   # solo los necesarios
  endpoint:
    health:
      show-details: when_authorized

// ✅ CORRECTO — Spring Security — deny by default
http
  .authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/v1/auth/**").permitAll()
    .anyRequest().authenticated()  // todo lo demás requiere auth
  )
```

#### A06 — Vulnerable Components

```
// Regla CI: mvn dependency-check:check (OWASP Dependency Check)
// Bloquea el build si hay vulnerabilidades CVSS >= 7.0
// Actualización de dependencias: revisión mensual con Dependabot
```

#### A07 — Authentication Failures

```java
// ✅ CORRECTO — respuesta idéntica para usuario no encontrado vs contraseña incorrecta
// (evitar user enumeration)
public AuthResponse login(LoginRequest request) {
  Optional<User> user = userRepository.findByEmailAndCompanyId(request.getEmail(), companyId);

  if (user.isEmpty() || !passwordEncoder.matches(request.getPassword(), user.get().getPasswordHash())) {
    // MISMO mensaje y MISMO tiempo de respuesta para ambos casos
    throw new InvalidCredentialsException("Email o contraseña incorrectos");
  }
  // ...
}

// ❌ INCORRECTO — mensajes diferenciados permiten enumerar usuarios
if (user.isEmpty()) throw new NotFoundException("Usuario no encontrado");
if (!passwordMatches) throw new InvalidCredentialsException("Contraseña incorrecta");
```

#### A09 — Security Logging and Monitoring

```java
// ✅ CORRECTO — loggear eventos de seguridad sin datos sensibles
log.warn("SECURITY: Failed login attempt for email={} from ip={}", maskedEmail, ipAddress);
log.info("SECURITY: User {} logged in from ip={}", userId, ipAddress);
log.error("SECURITY: Access denied for user={} on resource={}", userId, resourceId);

// ❌ PROHIBIDO — loggear datos sensibles
log.debug("Login attempt: email={}, password={}", email, password);  // contraseña en log
log.info("Token: {}", accessToken);  // token en log
```

### 5.2 Reglas adicionales de seguridad en código

```java
// REGLA: Nunca confiar en datos del cliente para identificar el tenant
// ❌
@PostMapping
public Response create(@RequestBody CreateRequest request) {
  service.create(request);  // request puede incluir companyId manipulado
}

// ✅
@PostMapping
public Response create(@RequestBody CreateRequest request, Authentication auth) {
  UUID companyId = ((JwtUser) auth.getPrincipal()).getCompanyId();  // del JWT
  service.create(request, companyId);
}

// REGLA: Validar con Bean Validation en el punto de entrada
@PostMapping
public ResponseEntity<PositionResponse> create(
    @Valid @RequestBody CreatePositionRequest request) {  // @Valid es obligatorio
  // ...
}

// REGLA: Los logs no deben contener PII sin enmascarar
private String maskEmail(String email) {
  int atIndex = email.indexOf('@');
  return email.substring(0, 2) + "***" + email.substring(atIndex);
  // ana@empresa.com → an***@empresa.com
}
```

---

## 6. Checkstyle — Configuración backend

El fichero de configuración completo se encuentra en `backend/config/checkstyle/checkstyle.xml`.

```xml
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
    "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
    "https://checkstyle.org/dtds/configuration_1_3.dtd">

<module name="Checker">
  <property name="severity" value="error"/>
  <property name="fileExtensions" value="java"/>

  <!-- ─── Archivos ─────────────────────────────────────────────── -->
  <module name="FileTabCharacter">
    <property name="eachLine" value="true"/>
  </module>
  <module name="NewlineAtEndOfFile"/>
  <module name="FileLength">
    <property name="max" value="500"/>
  </module>

  <module name="TreeWalker">

    <!-- ─── Imports ──────────────────────────────────────────────── -->
    <module name="AvoidStarImport"/>
    <module name="UnusedImports"/>
    <module name="IllegalImport">
      <property name="illegalPkgs" value="sun, com.sun"/>
    </module>
    <module name="ImportOrder">
      <property name="groups" value="java,javax,jakarta,org,com.recruitflow"/>
      <property name="separated" value="true"/>
      <property name="option" value="top"/>
    </module>

    <!-- ─── Nomenclatura ─────────────────────────────────────────── -->
    <module name="TypeName">
      <property name="format" value="^[A-Z][a-zA-Z0-9]*$"/>
    </module>
    <module name="MethodName">
      <property name="format" value="^[a-z][a-zA-Z0-9]*$"/>
    </module>
    <module name="ParameterName">
      <property name="format" value="^[a-z][a-zA-Z0-9]*$"/>
    </module>
    <module name="LocalVariableName">
      <property name="format" value="^[a-z][a-zA-Z0-9]*$"/>
    </module>
    <module name="ConstantName">
      <property name="format" value="^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"/>
    </module>
    <module name="PackageName">
      <property name="format" value="^[a-z]+(\.[a-z][a-z0-9]*)*$"/>
    </module>
    <module name="InterfaceTypeParameterName">
      <property name="format" value="^[A-Z][0-9]?$"/>
    </module>

    <!-- ─── Tamaño ────────────────────────────────────────────────── -->
    <module name="LineLength">
      <property name="max" value="100"/>
      <property name="ignorePattern" value="^package.*|^import.*|a href|href|http://|https://|ftp://"/>
    </module>
    <module name="MethodLength">
      <property name="max" value="40"/>
      <property name="tokens" value="METHOD_DEF, CTOR_DEF"/>
    </module>
    <module name="ParameterNumber">
      <property name="max" value="5"/>
    </module>

    <!-- ─── Complejidad ──────────────────────────────────────────── -->
    <module name="CyclomaticComplexity">
      <property name="max" value="10"/>
    </module>
    <module name="NPathComplexity">
      <property name="max" value="200"/>
    </module>
    <module name="BooleanExpressionComplexity">
      <property name="max" value="4"/>
    </module>

    <!-- ─── Llaves y espaciado ────────────────────────────────────── -->
    <module name="NeedBraces"/>
    <module name="LeftCurly">
      <property name="option" value="eol"/>
    </module>
    <module name="RightCurly">
      <property name="option" value="same"/>
    </module>
    <module name="EmptyBlock">
      <property name="option" value="TEXT"/>
    </module>
    <module name="WhitespaceAround">
      <property name="allowEmptyConstructors" value="true"/>
      <property name="allowEmptyMethods"      value="true"/>
      <property name="allowEmptyTypes"        value="true"/>
    </module>
    <module name="WhitespaceAfter"/>
    <module name="NoWhitespaceBefore"/>
    <module name="GenericWhitespace"/>
    <module name="Indentation">
      <property name="basicOffset"    value="2"/>
      <property name="braceAdjustment" value="0"/>
      <property name="caseIndent"     value="2"/>
      <property name="throwsIndent"   value="4"/>
      <property name="lineWrappingIndentation" value="4"/>
      <property name="arrayInitIndent" value="2"/>
    </module>

    <!-- ─── Javadoc ──────────────────────────────────────────────── -->
    <module name="JavadocMethod">
      <property name="scope"         value="public"/>
      <property name="allowUndeclaredRTE" value="true"/>
      <property name="tokens"        value="METHOD_DEF, ANNOTATION_FIELD_DEF"/>
    </module>
    <module name="MissingJavadocMethod">
      <property name="scope"         value="public"/>
      <property name="minLineCount"  value="3"/>
      <property name="tokens"        value="METHOD_DEF, ANNOTATION_FIELD_DEF"/>
    </module>
    <module name="JavadocType">
      <property name="scope" value="public"/>
    </module>

    <!-- ─── Buenas prácticas ─────────────────────────────────────── -->
    <module name="EqualsHashCode"/>
    <module name="FinalLocalVariable"/>
    <module name="HiddenField">
      <property name="ignoreSetter"        value="true"/>
      <property name="ignoreConstructorParameter" value="true"/>
    </module>
    <module name="IllegalInstantiation"/>
    <module name="MagicNumber">
      <property name="ignoreNumbers" value="-1, 0, 1, 2"/>
      <property name="ignoreAnnotation" value="true"/>
      <property name="ignoreHashCodeMethod" value="true"/>
    </module>
    <module name="MultipleVariableDeclarations"/>
    <module name="OneStatementPerLine"/>
    <module name="StringLiteralEquality"/>
    <module name="SuperClone"/>
    <module name="SuperFinalize"/>

    <!-- ─── Seguridad OWASP ──────────────────────────────────────── -->
    <module name="Regexp">
      <property name="format"  value="\.printStackTrace\(\)"/>
      <property name="message" value="OWASP: No usar printStackTrace(). Usar logger.error() con la excepción como argumento."/>
      <property name="illegalPattern" value="true"/>
    </module>
    <module name="Regexp">
      <property name="format"  value="System\.out\.print"/>
      <property name="message" value="No usar System.out. Usar SLF4J logger."/>
      <property name="illegalPattern" value="true"/>
    </module>
    <module name="Regexp">
      <property name="format"  value="catch\s*\(\s*Exception\s+\w+\s*\)\s*\{[^}]*\}"/>
      <property name="message" value="OWASP: No capturar Exception genérica. Usar excepciones específicas."/>
      <property name="illegalPattern" value="true"/>
    </module>
    <module name="Regexp">
      <property name="format"  value="TODO(?!.*#\d+)"/>
      <property name="message" value="Los TODOs deben referenciar un issue. Formato: // TODO #123 descripción"/>
      <property name="illegalPattern" value="true"/>
    </module>

    <!-- ─── Modificadores ────────────────────────────────────────── -->
    <module name="ModifierOrder">
      <property name="tokens" value="VARIABLE_DEF, METHOD_DEF, INTERFACE_DEF, CLASS_DEF, ANNOTATION_FIELD_DEF"/>
    </module>
    <module name="RedundantModifier"/>

  </module>
</module>
```

### 6.1 Integración con Maven

```xml
<!-- pom.xml -->
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-checkstyle-plugin</artifactId>
  <version>3.3.1</version>
  <configuration>
    <configLocation>config/checkstyle/checkstyle.xml</configLocation>
    <suppressionsLocation>config/checkstyle/suppressions.xml</suppressionsLocation>
    <violationSeverity>error</violationSeverity>
    <failOnViolation>true</failOnViolation>
    <includeTestSourceDirectory>true</includeTestSourceDirectory>
    <excludes>**/generated/**/*.java</excludes>
  </configuration>
  <executions>
    <execution>
      <id>checkstyle-validate</id>
      <phase>validate</phase>
      <goals><goal>check</goal></goals>
    </execution>
  </executions>
</plugin>
```

### 6.2 Supresiones permitidas

```xml
<!-- config/checkstyle/suppressions.xml -->
<?xml version="1.0"?>
<!DOCTYPE suppressions PUBLIC
    "-//Checkstyle//DTD SuppressionFilter Configuration 1.2//EN"
    "https://checkstyle.org/dtds/suppressions_1_2.dtd">

<suppressions>
  <!-- Código generado por OpenAPI Generator — no modificar manualmente -->
  <suppress files="generated" checks=".*"/>

  <!-- Migraciones SQL — sin límite de longitud de línea -->
  <suppress files="migration" checks="LineLength"/>

  <!-- Tests — relajar longitud de línea y número de parámetros -->
  <suppress files="Test\.java$"        checks="MethodLength" lines=".*"/>
  <suppress files="Test\.java$"        checks="MagicNumber"/>
  <suppress files="IT\.java$"          checks="MethodLength" lines=".*"/>
</suppressions>
```

---

## 7. ESLint + Prettier — Configuración frontend

### 7.1 ESLint

```javascript
// .eslintrc.cjs
module.exports = {
  root: true,
  env: {
    browser: true,
    es2022:  true,
    node:    true,
  },
  parser: '@typescript-eslint/parser',
  parserOptions: {
    ecmaVersion:       'latest',
    sourceType:        'module',
    ecmaFeatures:      { jsx: true },
    project:           ['./tsconfig.json'],
    tsconfigRootDir:   __dirname,
  },
  settings: {
    react: { version: 'detect' },
  },
  plugins: [
    '@typescript-eslint',
    'react',
    'react-hooks',
    'jsx-a11y',
    'import',
    'security',        // eslint-plugin-security — reglas OWASP
    'no-secrets',      // detectar secrets hardcoded
  ],
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended-type-checked',
    'plugin:@typescript-eslint/stylistic-type-checked',
    'plugin:react/recommended',
    'plugin:react/jsx-runtime',
    'plugin:react-hooks/recommended',
    'plugin:jsx-a11y/recommended',
    'plugin:import/recommended',
    'plugin:import/typescript',
    'plugin:security/recommended',
    'prettier',                         // desactiva reglas que conflictúan con Prettier
  ],
  rules: {
    // ─── TypeScript ──────────────────────────────────────────────
    '@typescript-eslint/no-explicit-any':              'error',
    '@typescript-eslint/no-non-null-assertion':        'error',
    '@typescript-eslint/explicit-function-return-type':'off',     // solo en exports públicos
    '@typescript-eslint/explicit-module-boundary-types':'warn',
    '@typescript-eslint/no-unused-vars': [
      'error',
      { argsIgnorePattern: '^_', varsIgnorePattern: '^_' },
    ],
    '@typescript-eslint/consistent-type-imports': [
      'error',
      { prefer: 'type-imports', disallowTypeAnnotations: false },
    ],
    '@typescript-eslint/no-floating-promises':         'error',
    '@typescript-eslint/await-thenable':               'error',
    '@typescript-eslint/no-misused-promises':          'error',

    // ─── React ──────────────────────────────────────────────────
    'react/prop-types':                     'off',      // TypeScript lo hace
    'react/display-name':                   'warn',
    'react-hooks/rules-of-hooks':           'error',
    'react-hooks/exhaustive-deps':          'warn',
    'react/no-danger':                      'error',    // OWASP: prevenir XSS con dangerouslySetInnerHTML
    'react/no-danger-with-children':        'error',
    'react/jsx-no-target-blank':            'error',    // OWASP: target="_blank" sin rel="noopener"

    // ─── Imports ────────────────────────────────────────────────
    'import/order': [
      'error',
      {
        groups: ['builtin','external','internal','parent','sibling','index','type'],
        'newlines-between': 'always',
        alphabetize: { order: 'asc', caseInsensitive: true },
      },
    ],
    'import/no-duplicates':     'error',
    'import/no-cycle':          'error',
    'import/no-default-export': 'warn',    // preferir named exports

    // ─── Seguridad OWASP ────────────────────────────────────────
    'security/detect-object-injection':     'warn',
    'security/detect-non-literal-regexp':   'warn',
    'security/detect-unsafe-regex':         'error',
    'security/detect-buffer-noassert':      'error',
    'security/detect-possible-timing-attacks': 'warn',
    'no-secrets/no-secrets':                'error',    // API keys, tokens hardcoded

    // ─── Calidad general ────────────────────────────────────────
    'no-console': ['warn', { allow: ['warn', 'error'] }],  // solo warn/error en prod
    'no-debugger':              'error',
    'no-alert':                 'error',
    'prefer-const':             'error',
    'no-var':                   'error',
    'eqeqeq':                   ['error', 'always'],
    'no-implicit-coercion':     'error',
    'no-param-reassign':        ['error', { props: true }],

    // ─── Accesibilidad ──────────────────────────────────────────
    'jsx-a11y/anchor-is-valid':             'error',
    'jsx-a11y/interactive-supports-focus':  'error',
    'jsx-a11y/no-autofocus':                'warn',
  },
  overrides: [
    // Archivos de test — relajar algunas reglas
    {
      files: ['**/*.test.{ts,tsx}', '**/*.spec.{ts,tsx}', 'cypress/**/*.ts'],
      rules: {
        '@typescript-eslint/no-non-null-assertion':   'off',
        '@typescript-eslint/no-explicit-any':          'warn',
        'import/no-default-export':                    'off',
        'no-secrets/no-secrets':                       'off',
      },
    },
    // Archivos de configuración de herramientas
    {
      files: ['*.config.{ts,js,cjs}', 'vite.config.*', 'jest.config.*'],
      rules: {
        'import/no-default-export': 'off',
      },
    },
  ],
  ignorePatterns: [
    'dist/',
    'build/',
    'src/generated/',
    'node_modules/',
    '*.min.js',
  ],
}
```

### 7.2 Prettier

```json
// .prettierrc
{
  "semi":               true,
  "singleQuote":        true,
  "jsxSingleQuote":     false,
  "trailingComma":      "all",
  "printWidth":         100,
  "tabWidth":           2,
  "useTabs":            false,
  "bracketSpacing":     true,
  "bracketSameLine":    false,
  "arrowParens":        "always",
  "endOfLine":          "lf",
  "importOrderSeparation": true,
  "importOrderSortSpecifiers": true
}
```

```
# .prettierignore
dist/
build/
src/generated/
*.min.js
*.min.css
CHANGELOG.md
```

### 7.3 Integración con scripts npm

```json
// package.json
{
  "scripts": {
    "lint":         "eslint . --ext .ts,.tsx --report-unused-disable-directives",
    "lint:fix":     "eslint . --ext .ts,.tsx --fix",
    "format":       "prettier --write \"src/**/*.{ts,tsx,css,json}\"",
    "format:check": "prettier --check \"src/**/*.{ts,tsx,css,json}\"",
    "type-check":   "tsc --noEmit",
    "validate":     "npm run type-check && npm run lint && npm run format:check"
  }
}
```

### 7.4 Husky + lint-staged — pre-commit hooks

```bash
# Instalar
npm install --save-dev husky lint-staged
npx husky init
```

```json
// package.json
{
  "lint-staged": {
    "src/**/*.{ts,tsx}": [
      "eslint --fix",
      "prettier --write"
    ],
    "src/**/*.{css,json,md}": [
      "prettier --write"
    ]
  }
}
```

```bash
# .husky/pre-commit
#!/bin/sh
npx lint-staged

# .husky/pre-push
#!/bin/sh
npm run type-check
```

---

## 8. Git y commits

### 8.1 Conventional Commits

Formato obligatorio: `<tipo>(<scope>): <descripción en imperativo>`

| Tipo | Cuándo usarlo |
|---|---|
| `feat` | Nueva funcionalidad |
| `fix` | Corrección de bug |
| `refactor` | Cambio de código sin cambiar comportamiento |
| `test` | Añadir o corregir tests |
| `docs` | Solo documentación |
| `style` | Formato, espacios, punto y coma (sin cambio de lógica) |
| `perf` | Mejora de rendimiento |
| `ci` | Cambios en CI/CD |
| `chore` | Tareas de mantenimiento (dependencias, config) |
| `revert` | Revertir un commit anterior |

```bash
# ✅ CORRECTO
feat(candidatos): add CV parsing with skill extraction
fix(matching): correct score calculation when candidate has no location
test(pipeline): add RBAC matrix parameterized tests for stage transitions
refactor(auth): extract token validation to dedicated JwtValidator class
docs(api): update OpenAPI spec for /offers endpoint

# ❌ INCORRECTO
fixed bug
WIP
cambios varios
update
```

### 8.2 Ramas

```
main              ← producción (protegida, solo merge via PR)
develop           ← integración continua
feature/<ticket>-<descripcion-corta>   ← nueva funcionalidad
fix/<ticket>-<descripcion-corta>       ← bug fix
refactor/<descripcion-corta>
release/<version>

# Ejemplos
feature/RF-42-cv-parsing
fix/RF-87-matching-score-null
refactor/candidate-module-hexagonal
```

### 8.3 Pull Requests

- **Tamaño máximo recomendado**: 400 líneas cambiadas. PRs más grandes requieren justificación.
- **Descripción obligatoria**: qué, por qué, cómo probar.
- **Un PR = una responsabilidad**: no mezclar features con refactors.
- **Todos los checks verdes** antes de solicitar revisión.
- **Squash merge** en main para historial limpio.

---

## 9. Revisión de código (Code Review)

### 9.1 Checklist del autor antes de abrir PR

```
[ ] Los tests pasan localmente (unit + integration)
[ ] La cobertura no ha decrementado
[ ] No hay warnings de Checkstyle/ESLint
[ ] No hay System.out ni printStackTrace ni console.log (excepto warn/error)
[ ] No hay TODO sin referencia a issue
[ ] Los nombres son descriptivos y siguen las convenciones
[ ] Los datos de otro tenant devuelven 404, no 403
[ ] Los inputs están validados con @Valid / Zod / Bean Validation
[ ] No hay credenciales, tokens ni secrets hardcoded
[ ] El PR tiene descripción clara con instrucciones para probar
```

### 9.2 Checklist del revisor

```
[ ] La lógica de negocio es correcta según los criterios de aceptación
[ ] Los casos límite y de error están contemplados
[ ] Los tests cubren la lógica nueva y los casos de error
[ ] No hay vulnerabilidades de seguridad evidentes (IDOR, injection, XSS)
[ ] El tenant isolation está aplicado en todas las queries nuevas
[ ] Los nombres son claros y coherentes con el resto del módulo
[ ] No hay complejidad innecesaria (YAGNI)
[ ] Los comentarios inline aportan valor (explican el "por qué", no el "qué")
```

### 9.3 Etiquetas de comentario en PR

| Etiqueta | Significado |
|---|---|
| `[bloqueante]` | Debe resolverse antes del merge |
| `[sugerencia]` | Mejora no obligatoria |
| `[pregunta]` | Solicitud de aclaración |
| `[nit]` | Detalle menor de estilo |
| `[OWASP]` | Problema de seguridad — siempre bloqueante |

```
// Ejemplos de comentarios de revisión

[bloqueante] OWASP A01: Esta query no filtra por company_id.
Un usuario podría ver candidatos de otro tenant.

[sugerencia] Podrías extraer esta lógica a un método privado
calculateFinalScore() para mejorar la legibilidad.

[nit] El nombre `data` no es descriptivo. Considera `matchingResults`.

[pregunta] ¿Por qué se usa Optional.get() aquí sin comprobar isPresent()?
```

---

*Guía de Estilo RecruitFlow v1.0 — Cumplimiento obligatorio desde el primer sprint*
*Las excepciones deben ser aprobadas por el Tech Lead y documentadas en el ADR correspondiente*
*Próxima revisión: 2026-07-04*
