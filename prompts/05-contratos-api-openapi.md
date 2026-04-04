# Prompt 05 — Contratos API y especificación OpenAPI

**Documentos generados:** `docs/architecture/project.md` (secciones 10–11) · `docs/architecture/openapi.yaml`

---

## Prompt utilizado

```
Genera en project.md los contratos completos de la API REST de RecruitFlow por módulo
y el esquema OpenAPI 3.0 en YAML que sirva como contrato Front-Back.

Para cada uno de los 9 módulos (vacantes, candidatos, matching, pipeline, entrevistas,
pruebas técnicas, propuestas, comunicaciones, administración) incluye:

**Contratos por módulo:**
- Lista de endpoints: método HTTP + path
- Request body con schema JSON y tipos de campo
- Tabla de validaciones (campo, tipo, requerido, reglas)
- Response schema (éxito y error)
- Códigos HTTP que puede devolver cada endpoint y su significado

**OpenAPI 3.0 (openapi.yaml):**
- openapi: 3.0.3
- Info: título, versión, descripción
- Servers: localhost:8080 y producción
- SecuritySchemes: BearerAuth (JWT)
- Paths: todos los endpoints de los 9 módulos con:
  * operationId
  * tags
  * summary y description
  * parameters (path, query, header)
  * requestBody con $ref a schemas
  * responses con $ref a schemas
  * security: [BearerAuth]
- Components/schemas: ~60 schemas reutilizables con todas las entidades,
  enums (PositionStatus, ApplicationStage, InterviewType, ContractType, UserRole),
  requests, responses y tipos paginados (Page<T>)
- Components/parameters: companyId, page, size, sort
- Components/responses: 400 BadRequest, 401 Unauthorized, 403 Forbidden,
  404 NotFound, 409 Conflict, 500 InternalServerError

El archivo openapi.yaml debe ir en docs/architecture/openapi.yaml.
El spec debe ser completo y usable directamente con Swagger UI o Redoc.
```

---

*Sesión: AI4Devs Design — RecruitFlow*
