# Prompt 08 — Guía de estilo de código

**Documentos generados:** `docs/quality/code-style-guide.md` · `config/checkstyle/checkstyle.xml` · `config/checkstyle/suppressions.xml` · `config/frontend/.eslintrc.cjs` · `config/frontend/.prettierrc` · `config/frontend/.prettierignore` · `config/frontend/.editorconfig`

---

## Prompt utilizado

```
Eres un experto en desarrollo de software y tienes que generar una Guía de estilo de código
con Google Style y OWASP para desarrollo seguro, Checkstyle, ESLint config y convenciones de naming.

El stack es:
- Backend: Java 21, Spring Boot 3.3, arquitectura hexagonal
- Frontend: React 18, TypeScript, Vite
- Linting backend: Checkstyle
- Linting frontend: ESLint + Prettier
- Seguridad: OWASP Top 10 integrado en las reglas de lint

Genera los siguientes documentos y ficheros de configuración:

1. **docs/quality/code-style-guide.md** — Guía completa con:

   **Backend (Google Java Style):**
   - Indentación: 2 espacios (no tabs)
   - Longitud de línea: 100 caracteres
   - Ordenación de imports (estático, java.*, javax.*, org.*, com.*, proyecto)
   - Ordenación de miembros de clase
   - Convenciones de naming por capa de arquitectura hexagonal:
     * Domain: entidades (sin sufijo), value objects, Events, Exceptions
     * Application: UseCases, Repositories (interfaces), Services
     * Application DTOs: Request/Command, Response
     * Infrastructure: JPA Entities, Adapters, Mappers, Controllers, Aspects, Config
   - Reglas OWASP con ejemplos de código correcto e incorrecto:
     * IDOR prevention (nunca exponer IDs internos)
     * SQL injection (solo JPA/JPQL, nunca concatenación)
     * Cryptographic failures (BCrypt, AES-256, no MD5/SHA-1)
     * Logging seguro (nunca loggear passwords, tokens, PII)
     * Error handling (no printStackTrace, no Exception genérica)

   **Frontend (TypeScript + React):**
   - Convenciones de naming:
     * Componentes: PascalCase
     * Hooks: use + PascalCase
     * Handlers: handle + Action
     * Callbacks props: on + Verb
     * Stores Zustand: use + Name + Store
     * Tipos/Interfaces: PascalCase (sin prefijo I)
   - Reglas ESLint: hooks, exhaustive-deps, jsx-a11y, import ordering
   - Reglas de seguridad: no dangerouslySetInnerHTML, no eval, no object injection

   **Base de datos:**
   - Nombres de tablas y columnas: snake_case
   - Índices: idx_tabla_columnas
   - Unique constraints: uq_tabla_columnas
   - Foreign keys: fk_tabla_referencia

   **Git:**
   - Conventional Commits: feat/fix/refactor/test/docs/chore con scope
   - Formato: tipo(scope): descripción imperativa en presente
   - Ejemplos para cada tipo
   - Naming de ramas: feature/RF-XX-slug, task/RF-XX-slug, bugfix/RF-XX-slug

   **Pull Requests:**
   - Checklist de PR
   - Etiquetas de revisión de código

2. **config/checkstyle/checkstyle.xml** — Configuración Checkstyle completa:
   - Google Style rules (indent 2, line length 100, import ordering)
   - Módulos OWASP con Regexp:
     * No printStackTrace()
     * No System.out/err
     * No new java.util.Random()
     * No MD5/SHA-1
     * No logging de passwords
     * TODO debe incluir número de issue (#123)
   - IllegalCatch / IllegalThrows para Exception/RuntimeException/Throwable
   - JavadocMethod para public methods

3. **config/checkstyle/suppressions.xml** — Supresiones para:
   - Código generado (generated/)
   - Migraciones SQL
   - Ficheros de test
   - Object Mothers
   - Clases Aspect

4. **config/frontend/.eslintrc.cjs** — ESLint con:
   - TypeScript strict, React hooks, jsx-a11y, import ordering
   - eslint-plugin-security (object injection, unsafe regex, timing attacks)
   - eslint-plugin-no-secrets (detección de API keys por entropía)
   - react/no-danger (bloquea dangerouslySetInnerHTML)
   - Overrides por tipo de fichero (tests, Cypress, config, MSW handlers)

5. **config/frontend/.prettierrc** — singleQuote, 100 printWidth, trailingComma all, LF

6. **config/frontend/.editorconfig** — 2 espacios para Java/TS/JSON/YAML, 4 para SQL

Todos los ficheros de configuración deben ser production-ready y listos para copiar al proyecto.
```

---

*Sesión: AI4Devs Design — RecruitFlow*
