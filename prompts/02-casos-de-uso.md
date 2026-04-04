# Prompt 02 — Casos de uso

**Documento generado:** `docs/product/casos-de-uso.md`

---

## Prompt utilizado

```
Eres un analista de sistemas experto en UML y diseño de software.

A partir del PRD de RecruitFlow genera el documento de casos de uso con:

1. **Diagrama de actores** — identifica todos los actores del sistema:
   - Actores primarios: Admin, Recruiter, Candidato
   - Actores secundarios: Sistema de email, WhatsApp, Plataformas de pruebas técnicas

2. **44 casos de uso** organizados por módulo:
   - Gestión de vacantes (crear, editar, publicar, cerrar, duplicar vacante)
   - Gestión de candidatos (registrar, buscar, importar CV, actualizar perfil)
   - Pipeline de selección (mover candidato entre etapas, vista Kanban)
   - Matching automático (lanzar matching, revisar score, aceptar/descartar)
   - Entrevistas (agendar, confirmar, registrar feedback, cancelar)
   - Pruebas técnicas (asignar, enviar enlace, revisar resultado)
   - Propuestas (generar, enviar, registrar aceptación/rechazo)
   - Comunicaciones (enviar email, enviar WhatsApp, ver historial)
   - Administración (gestionar usuarios, configurar empresa, ver reportes)

3. Para cada caso de uso incluir:
   - ID y nombre
   - Actor principal
   - Precondiciones
   - Flujo principal (pasos numerados)
   - Flujos alternativos / excepciones
   - Postcondiciones

4. **Diagramas Mermaid** para los flujos más complejos (pipeline de selección, matching, flujo de entrevista).

Contexto del sistema: multi-tenant SaaS, roles ADMIN y RECRUITER, stack Spring Boot + React.
```

---

*Sesión: AI4Devs Design — RecruitFlow*
