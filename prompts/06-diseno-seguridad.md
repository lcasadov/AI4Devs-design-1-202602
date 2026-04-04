# Prompt 06 — Diseño de seguridad

**Documento generado:** `docs/security/security-design.md`

---

## Prompt utilizado

```
Eres un experto en seguridad de aplicaciones web y sistemas SaaS multi-tenant.

Realiza el diseño de seguridad detallado para RecruitFlow con los siguientes apartados:

1. **Modelo de seguridad en capas** (4 capas):
   - Capa 1: nginx (TLS, headers de seguridad, WAF básico)
   - Capa 2: JWT (validación de token en cada request)
   - Capa 3: RBAC + AOP (autorización por rol y operación)
   - Capa 4: JPA Filter (aislamiento de tenant por company_id)

2. **Matriz de roles y permisos** (ADMIN vs RECRUITER):
   Para cada uno de los 11 módulos (vacantes, candidatos, matching, pipeline,
   entrevistas, pruebas, propuestas, comunicaciones, reportes, configuración, usuarios)
   indica qué puede hacer cada rol usando ✅ (permitido) / 🔒 (solo los propios) / ❌ (denegado)

3. **Flujo detallado de JWT**:
   - Emisión: estructura del token RS256, claims incluidos (sub, company_id, role,
     permissions[], jti, iat, exp), tiempo de vida (access: 15 min, refresh: 7 días)
   - Refresco: diagrama de secuencia Mermaid con cola de requests durante el refresh
   - Revocación: tabla de escenarios (logout, cambio de contraseña, cuenta suspendida,
     token robado) con mecanismo de invalidación
   - Validación por request: diagrama de flujo completo

4. **Política de CORS**:
   - Configuración nginx (map block para origins permitidos)
   - Configuración Spring CorsConfigurationSource
   - Headers permitidos, métodos, max-age

5. **Rate limiting**:
   - Tabla de límites por endpoint y rol
   - Implementación con Redis (sliding window)
   - Headers de respuesta (X-RateLimit-*)
   - Comportamiento al superar límite (429 + Retry-After)

6. **Scopes por tenant (multi-tenancy)**:
   - Cómo se implementa el aislamiento con @Filter de Hibernate
   - Política de respuesta para acceso cross-tenant: 404 (no 403) para evitar enumeración
   - Auditoría de accesos (AOP + audit_log)

7. **Configuración de seguridad adicional**:
   - Política de contraseñas (bcrypt, complejidad mínima)
   - Protección CSRF (SameSite cookie + custom header)
   - Content Security Policy headers
   - OWASP Top 10: medidas implementadas para cada categoría

Incluye diagramas Mermaid (sequenceDiagram, flowchart) para los flujos clave.
```

---

*Sesión: AI4Devs Design — RecruitFlow*
