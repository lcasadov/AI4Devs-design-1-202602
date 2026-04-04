# Prompts — RecruitFlow

Colección de prompts utilizados para generar toda la documentación del proyecto RecruitFlow
con asistencia de IA (Claude).

---

## Índice

| # | Prompt | Documentos generados |
|---|--------|----------------------|
| 01 | [Visión del producto y PRD](./01-vision-y-prd.md) | `docs/product/vision.md`, `docs/product/PRD.md` |
| 02 | [Casos de uso](./02-casos-de-uso.md) | `docs/product/casos-de-uso.md` |
| 03 | [Arquitectura del sistema](./03-arquitectura.md) | `docs/architecture/project.md` |
| 04 | [Modelo de datos](./04-modelo-de-datos.md) | `docs/architecture/data-model.md`, `docs/architecture/data-model-additional.md` |
| 05 | [Contratos API y OpenAPI](./05-contratos-api-openapi.md) | `docs/architecture/project.md` (secciones 10–11), `docs/architecture/openapi.yaml` |
| 06 | [Diseño de seguridad](./06-diseno-seguridad.md) | `docs/security/security-design.md` |
| 07 | [Estrategia de testing](./07-testing-strategy.md) | `docs/quality/testing-strategy.md` |
| 08 | [Guía de estilo de código](./08-code-style-guide.md) | `docs/quality/code-style-guide.md`, `config/checkstyle/`, `config/frontend/` |

---

## Cómo usar estos prompts

Cada fichero contiene el prompt exacto (o una reconstrucción fiel) utilizado con Claude.
Puedes reutilizarlos para:

- **Regenerar** un documento desde cero si necesitas una revisión mayor
- **Adaptar** el prompt a otro proyecto cambiando el nombre, stack y módulos
- **Extender** añadiendo secciones al prompt original para obtener más detalle

### Recomendaciones

1. Proporciona siempre el contexto del stack tecnológico al inicio del prompt.
2. Indica el formato de salida esperado (Mermaid, JSON, YAML, Markdown).
3. Para documentos largos, divide el prompt en secciones y genera por partes.
4. Usa el documento anterior como contexto para el siguiente (encadenamiento).

---

## Orden recomendado de generación

```
01 Visión + PRD
      ↓
02 Casos de uso
      ↓
03 Arquitectura (C4 + hexagonal + ADRs)
      ↓
04 Modelo de datos
      ↓
05 Contratos API + OpenAPI spec
      ↓
06 Diseño de seguridad ──┐
07 Testing strategy      ├─ (paralelo)
08 Code style guide    ──┘
```

---

*Proyecto: RecruitFlow — AI4Devs Design*
