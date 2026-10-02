# Implementación de un sistema de validación de extremo a extremo en un flujo de crédito

En el contexto de un flujo de crédito, el sistema debe validar las solicitudes de crédito de extremo a extremo, asegurando que los datos cumplan con los requisitos de negocio y las restricciones operativas. El flujo involucra múltiples actores, incluyendo el originador de créditos, el motor antifraude, el buró de riesgos y el core bancario. Las solicitudes de crédito deben ser validadas en términos de consistencia de datos, ausencia de fraude y cumplimiento de los límites establecidos por el buró de riesgos. El sistema debe manejar la idempotencia de las solicitudes, asegurando que una solicitud repetida no genere duplicados, y debe ser capaz de recuperarse de fallos temporales en los servicios externos.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | E2E inval |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Validación inicial de solicitudes de crédito

**Objetivo:** Implementar la validación básica de solicitudes de crédito, asegurando la consistencia de datos y la idempotencia.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- El sistema debe validar que las solicitudes de crédito contengan todos los campos requeridos y que los datos sean consistentes.
- Implementar la idempotencia para que las solicitudes repetidas con la misma clave no generen duplicados.
- Manejar los errores de validación y proporcionar respuestas adecuadas al originador de créditos.

**Entregable:** Sistema que valida las solicitudes de crédito y asegura la idempotencia.

<details>
<summary>Pistas de conocimiento</summary>

- Considerar cómo manejar los datos faltantes o inconsistentes.
- Explorar diferentes estrategias para implementar la idempotencia.

</details>

### Fase 2: Integración con motor antifraude y buró de riesgos

**Objetivo:** Integrar el sistema de validación con el motor antifraude y el buró de riesgos para asegurar la ausencia de fraude y el cumplimiento de los límites establecidos.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- El sistema debe enviar las solicitudes de crédito al motor antifraude y al buró de riesgos para validación adicional.
- Implementar la lógica para manejar las respuestas del motor antifraude y del buró de riesgos.
- Asegurar que las solicitudes que no cumplen con los requisitos sean rechazadas adecuadamente.

**Entregable:** Sistema integrado con motor antifraude y buró de riesgos, capaz de manejar las respuestas y rechazar solicitudes no válidas.

<details>
<summary>Pistas de conocimiento</summary>

- Considerar cómo manejar los timeouts y las respuestas de error de los servicios externos.
- Explorar diferentes estrategias para integrar los servicios externos.

</details>

### Fase 3: Recuperación de fallos y reintentos

**Objetivo:** Implementar la lógica de recuperación de fallos y reintentos para asegurar la robustez del sistema.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- El sistema debe ser capaz de recuperarse de fallos temporales en los servicios externos y reintentar las solicitudes de crédito.
- Implementar la lógica de reintentos con una estrategia adecuada para evitar sobrecargar los servicios externos.
- Asegurar que las solicitudes reintentadas no generen duplicados.

**Entregable:** Sistema robusto con lógica de recuperación de fallos y reintentos.

<details>
<summary>Pistas de conocimiento</summary>

- Considerar diferentes estrategias de reintento, como backoff exponencial.
- Explorar cómo manejar los duplicados en caso de reintentos.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es la validación de extremo a extremo en el contexto de un flujo de crédito?
- **paraQueSirve**: ¿Para qué sirve la integración con el motor antifraude y el buró de riesgos en el flujo de crédito?
- **comoSeUsa**: ¿Cómo se usa la lógica de recuperación de fallos y reintentos en el sistema de validación?
- **erroresComunes**: ¿Cuáles son los errores comunes que pueden ocurrir durante la validación de solicitudes de crédito?
- **queDecisionesImplica**: ¿Qué decisiones implica la implementación de la lógica de recuperación de fallos y reintentos?

## Criterios de Evaluacion

- Implementación de la validación básica de solicitudes de crédito.
- Integración con motor antifraude y buró de riesgos.
- Implementación de la lógica de recuperación de fallos y reintentos.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
