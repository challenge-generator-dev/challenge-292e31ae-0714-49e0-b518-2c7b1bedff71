# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/pragma/creditflow/infrastructure/config/ResilienceConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Archivos que la arquitectura del reto declara y no estan

Creálos con implementacion real, en la capa que les corresponde:

- `src/main/java/com/ragma/creditflow/infrastructure/exception/CustomException.java`
- `src/main/java/com/ragma/creditflow/infrastructure/exception/ValidationException.java`

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/IdempotencyAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/creditflow/infrastructure/exception/GlobalExceptionHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/creditflow/CreditFlowApplication.java` — `CreditFlowProperties.getCircuitBreaker`: Se invoca `getCircuitBreaker` sobre `CreditFlowProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/CreditFlowApplication.java` — `CreditFlowProperties.getTimeout`: Se invoca `getTimeout` sobre `CreditFlowProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/CreditFlowApplication.java` — `CreditFlowProperties.getFraudEngine`: Se invoca `getFraudEngine` sobre `CreditFlowProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/CreditFlowApplication.java` — `CreditFlowProperties.getRiskBureau`: Se invoca `getRiskBureau` sobre `CreditFlowProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `CreditRequest.applicantId`: Se invoca `applicantId` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `CreditRequest.id`: Se invoca `id` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `CreditRequest.status`: Se invoca `status` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `CreditRequest.isPresent`: Se invoca `isPresent` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `CreditRequest.get`: Se invoca `get` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `RiskBureauService.checkRisk`: Se invoca `checkRisk` sobre `RiskBureauService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `CreditRequest.requestedAmount`: Se invoca `requestedAmount` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/IdempotencyAdapter.java` — `IdempotencyEntity.getRequestId`: Se invoca `getRequestId` sobre `IdempotencyEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/IdempotencyAdapter.java` — `IdempotencyEntity.setIdempotencyKey`: Se invoca `setIdempotencyKey` sobre `IdempotencyEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/IdempotencyAdapter.java` — `IdempotencyEntity.setRequestId`: Se invoca `setRequestId` sobre `IdempotencyEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/IdempotencyAdapter.java` — `IdempotencyEntity.setCreatedAt`: Se invoca `setCreatedAt` sobre `IdempotencyEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.applicantId`: Se invoca `applicantId` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setId`: Se invoca `setId` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setCreatedAt`: Se invoca `setCreatedAt` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setUpdatedAt`: Se invoca `setUpdatedAt` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getId`: Se invoca `getId` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.id`: Se invoca `id` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setStatus`: Se invoca `setStatus` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.status`: Se invoca `status` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setApprovedAmount`: Se invoca `setApprovedAmount` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.approvedAmount`: Se invoca `approvedAmount` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setRejectionReason`: Se invoca `setRejectionReason` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.rejectionReason`: Se invoca `rejectionReason` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setApplicantId`: Se invoca `setApplicantId` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setRequestedAmount`: Se invoca `setRequestedAmount` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.requestedAmount`: Se invoca `requestedAmount` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setCreditPurpose`: Se invoca `setCreditPurpose` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.creditPurpose`: Se invoca `creditPurpose` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setTermMonths`: Se invoca `setTermMonths` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.termMonths`: Se invoca `termMonths` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getApplicantId`: Se invoca `getApplicantId` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getRequestedAmount`: Se invoca `getRequestedAmount` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getCreditPurpose`: Se invoca `getCreditPurpose` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getTermMonths`: Se invoca `getTermMonths` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getStatus`: Se invoca `getStatus` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getApprovedAmount`: Se invoca `getApprovedAmount` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getRejectionReason`: Se invoca `getRejectionReason` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getCreatedAt`: Se invoca `getCreatedAt` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getUpdatedAt`: Se invoca `getUpdatedAt` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java` — `CreditRequest.requestId`: Se invoca `requestId` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java` — `CreditRequest.documentNumber`: Se invoca `documentNumber` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java` — `CreditRequest.amount`: Se invoca `amount` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java` — `CreditRequest.termMonths`: Se invoca `termMonths` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java` — `CreditRequest.createdAt`: Se invoca `createdAt` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java` — `CreditRequest.documentNumber`: Se invoca `documentNumber` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java` — `CreditRequest.amount`: Se invoca `amount` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java` — `CreditRequest.termMonths`: Se invoca `termMonths` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java` — `CreditRequest.creditType`: Se invoca `creditType` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java` — `CreditRequest.requestId`: Se invoca `requestId` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequestUseCase.processRequest`: Se invoca `processRequest` sobre `CreditRequestUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `ValidationException.getCode`: Se invoca `getCode` sobre `ValidationException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `ValidationException.getMessage`: Se invoca `getMessage` sobre `ValidationException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `RiskBureauService.evaluateRisk`: Se invoca `evaluateRisk` sobre `RiskBureauService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequest.status`: Se invoca `status` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequest.rejectionReason`: Se invoca `rejectionReason` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequest.clientId`: Se invoca `clientId` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequest.amount`: Se invoca `amount` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequest.termMonths`: Se invoca `termMonths` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequest.approvedAt`: Se invoca `approvedAt` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapterTest.java` — `ServiceUnavailableException.getMessage`: Se invoca `getMessage` sobre `ServiceUnavailableException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapterTest.java` — `ServiceUnavailableException.getCode`: Se invoca `getCode` sobre `ServiceUnavailableException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapterTest.java` — `RiskBureauAdapter.evaluateRisk`: Se invoca `evaluateRisk` sobre `RiskBureauAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapterTest.java` — `ServiceUnavailableException.getMessage`: Se invoca `getMessage` sobre `ServiceUnavailableException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapterTest.java` — `ServiceUnavailableException.getCode`: Se invoca `getCode` sobre `ServiceUnavailableException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Spring, Tecnología Java, Senior L2

### Brecha de conocimiento
x

### Reto
- Tema: E2E inval
- Seniority: senior-l2
- Tipo: practical
- Título: Implementación de un sistema de validación de extremo a extremo en un flujo de crédito
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Validación inicial de solicitudes de crédito — objetivo: Implementar la validación básica de solicitudes de crédito, asegurando la consistencia de datos y la idempotencia. — entregable (NO resolver): Sistema que valida las solicitudes de crédito y asegura la idempotencia.
- Fase 2: Integración con motor antifraude y buró de riesgos — objetivo: Integrar el sistema de validación con el motor antifraude y el buró de riesgos para asegurar la ausencia de fraude y el cumplimiento de los límites establecidos. — entregable (NO resolver): Sistema integrado con motor antifraude y buró de riesgos, capaz de manejar las respuestas y rechazar solicitudes no válidas.
- Fase 3: Recuperación de fallos y reintentos — objetivo: Implementar la lógica de recuperación de fallos y reintentos para asegurar la robustez del sistema. — entregable (NO resolver): Sistema robusto con lógica de recuperación de fallos y reintentos.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>creditflow</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>creditflow</name>
    <description>Sistema de validación de extremo a extremo para flujo de crédito</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-reactor</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- OpenAPI -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.9.1</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/pragma/creditflow/CreditFlowApplication.java ===
package com.pragma.creditflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import java.time.Duration;

@SpringBootApplication
@EnableConfigurationProperties
public class CreditFlowApplication {
    private final CreditFlowProperties creditFlowProperties;

    public CreditFlowApplication(CreditFlowProperties creditFlowProperties) {
        this.creditFlowProperties = creditFlowProperties;
        validateProperties();
    }

    public static void main(String[] args) {
        SpringApplication.run(CreditFlowApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CircuitBreakerConfig defaultCircuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(creditFlowProperties.getCircuitBreaker().getFailureRateThreshold())
                .waitDurationInOpenState(Duration.ofMillis(creditFlowProperties.getCircuitBreaker().getWaitDurationInOpenState()))
                .slidingWindowSize(creditFlowProperties.getCircuitBreaker().getSlidingWindowSize())
                .build();
    }

    @Bean
    public TimeLimiterConfig defaultTimeLimiterConfig() {
        return TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(creditFlowProperties.getTimeout().getDuration()))
                .build();
    }

    private void validateProperties() {
        if (creditFlowProperties.getFraudEngine().getBaseUrl() == null ||
            creditFlowProperties.getFraudEngine().getBaseUrl().isBlank()) {
            throw new IllegalStateException("La URL base del motor antifraude no puede estar vacía");
        }
        if (creditFlowProperties.getRiskBureau().getBaseUrl() == null ||
            creditFlowProperties.getRiskBureau().getBaseUrl().isBlank()) {
            throw new IllegalStateException("La URL base del buró de riesgos no puede estar vacía");
        }
        if (creditFlowProperties.getCircuitBreaker().getFailureRateThreshold() <= 0 ||
            creditFlowProperties.getCircuitBreaker().getFailureRateThreshold() > 100) {
            throw new IllegalStateException("El umbral de fallos del CircuitBreaker debe estar entre 1 y 100");
        }
    }

    public record CreditFlowProperties(
        FraudEngine fraudEngine,
        RiskBureau riskBureau,
        CircuitBreaker circuitBreaker,
        Timeout timeout
    ) {
        public record FraudEngine(String baseUrl, String endpoint) {}
        public record RiskBureau(String baseUrl, String endpoint) {}
        public record CircuitBreaker(
            int failureRateThreshold,
            int waitDurationInOpenState,
            int slidingWindowSize
        ) {}
        public record Timeout(long duration) {}
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: creditflow
  datasource:
    url: jdbc:postgresql://localhost:5432/creditflow
    username: creditflow_user
    password: creditflow_password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect

server:
  port: 8080

resilience4j:
  circuitbreaker:
    instances:
      fraudEngine:
        registerHealthIndicator: true
        failureRateThreshold: 50
        waitDurationInOpenState: 5000
        permittedNumberOfCallsInHalfOpenState: 3
        slidingWindowSize: 10
        slidingWindowType: COUNT_BASED
      riskBureau:
        registerHealthIndicator: true
        failureRateThreshold: 50
        waitDurationInOpenState: 5000
        permittedNumberOfCallsInHalfOpenState: 3
        slidingWindowSize: 10
        slidingWindowType: COUNT_BASED
  timelimiter:
    instances:
      fraudEngine:
        timeoutDuration: 2000
      riskBureau:
        timeoutDuration: 3000
  retry:
    instances:
      fraudEngine:
        maxAttempts: 3
        waitDuration: 1000
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
      riskBureau:
        maxAttempts: 3
        waitDuration: 1000
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2

management:
  endpoints:
    web:
      exposure:
        include: health,metrics,circuitbreakers
  endpoint:
    health:
      show-details: always

app:
  fraud-engine:
    base-url: http://localhost:8081
    endpoint: /api/fraud-check
  risk-bureau:
    base-url: http://localhost:8082
    endpoint: /api/risk-assessment
  circuit-breaker:
    failure-rate-threshold: 50
    wait-duration-in-open-state: 5000
    sliding-window-size: 10
  timeout:
    duration: 2000

// === ARCHIVO: src/main/java/com/pragma/creditflow/domain/model/CreditRequest.java ===
package com.pragma.creditflow.domain.model;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreditRequest(
    @NotNull(message = "El ID de la solicitud no puede ser nulo")
    UUID requestId,

    @NotNull(message = "El ID del cliente no puede ser nulo")
    UUID customerId,

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser positivo")
    BigDecimal amount,

    @NotNull(message = "La fecha de solicitud no puede ser nula")
    LocalDate requestDate,

    @NotNull(message = "El plazo en meses no puede ser nulo")
    @Min(value = 1, message = "El plazo en meses debe ser al menos 1")
    @Max(value = 360, message = "El plazo en meses no puede exceder 360")
    Integer termMonths,

    @NotBlank(message = "El propósito del crédito no puede estar vacío")
    @Size(max = 255, message = "El propósito del crédito no puede exceder 255 caracteres")
    String purpose,

    @NotNull(message = "El estado de la solicitud no puede ser nulo")
    CreditRequestStatus status,

    @NotNull(message = "La clave de idempotencia no puede ser nula")
    IdempotencyKey idempotencyKey
) {
    public enum CreditRequestStatus {
        PENDING,
        APPROVED,
        REJECTED,
        FRAUD_DETECTED,
        RISK_LIMIT_EXCEEDED
    }

    public CreditRequest {
        if (requestId == null) {
            requestId = UUID.randomUUID();
        }
        if (requestDate == null) {
            requestDate = LocalDate.now();
        }
    }

    public CreditRequest withStatus(CreditRequestStatus newStatus) {
        return new CreditRequest(
            this.requestId,
            this.customerId,
            this.amount,
            this.requestDate,
            this.termMonths,
            this.purpose,
            newStatus,
            this.idempotencyKey
        );
    }

    public boolean isApproved() {
        return CreditRequestStatus.APPROVED.equals(this.status);
    }

    public boolean isRejected() {
        return CreditRequestStatus.REJECTED.equals(this.status) ||
               CreditRequestStatus.FRAUD_DETECTED.equals(this.status) ||
               CreditRequestStatus.RISK_LIMIT_EXCEEDED.equals(this.status);
    }
}

// === ARCHIVO: src/main/java/com/pragma/creditflow/domain/model/IdempotencyKey.java ===
package com.pragma.creditflow.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

public final class IdempotencyKey {
    @NotBlank(message = "La clave de idempotencia no puede estar vacía")
    @Size(max = 64, message = "La clave de idempotencia no puede exceder 64 caracteres")
    private final String key;

    private IdempotencyKey(String key) {
        this.key = Objects.requireNonNull(key, "La clave de idempotencia no puede ser nula");
        if (key.isBlank()) {
            throw new IllegalArgumentException("La clave de idempotencia no puede estar vacía");
        }
        if (key.length() > 64) {
            throw new IllegalArgumentException("La clave de idempotencia no puede exceder 64 caracteres");
        }
    }

    public static IdempotencyKey of(String key) {
        return new IdempotencyKey(key);
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdempotencyKey that = (IdempotencyKey) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "IdempotencyKey{" +
                "key='" + key + '\'' +
                '}';
    }
}

// === ARCHIVO: src/main/java/com/ragma/creditflow/domain/port/IdempotencyRepository.java ===
package com.pragma.creditflow.domain.port;

import com.pragma.creditflow.domain.model.IdempotencyKey;
import java.util.Optional;
import java.util.UUID;

public interface IdempotencyRepository {
    /**
     * Verifica si ya existe una solicitud procesada con la clave de idempotencia dada.
     * @param idempotencyKey la clave de idempotencia a verificar
     * @return el ID de la solicitud asociada a la clave si existe, o Optional.empty() si no existe
     */
    Optional<UUID> findProcessedRequestId(IdempotencyKey idempotencyKey);

    /**
     * Guarda la asociación entre una clave de idempotencia y el ID de una solicitud procesada.
     * @param idempotencyKey la clave de idempotencia
     * @param requestId el ID de la solicitud procesada
     */
    void save(IdempotencyKey idempotencyKey, UUID requestId);
}

// === ARCHIVO: src/main/java/com/pragma/creditflow/domain/port/CreditRequestRepository.java ===
package com.pragma.creditflow.domain.port;

import com.pragma.creditflow.domain.model.CreditRequest;
import java.util.Optional;

/**
 * Puerto de persistencia para solicitudes de crédito.
 * Define las operaciones de acceso a datos que el dominio necesita.
 * La implementación concreta reside en la capa de infraestructura.
 */
public interface CreditRequestRepository {

    /**
     * Guarda una solicitud de crédito en el repositorio.
     * @param creditRequest la solicitud a persistir
     * @return la solicitud persistida con su identificador
     */
    CreditRequest save(CreditRequest creditRequest);

    /**
     * Busca una solicitud de crédito por su identificador único.
     * @param id el identificador de la solicitud
     * @return un Optional con la solicitud si existe, vacío si no
     */
    Optional<CreditRequest> findById(java.util.UUID id);

    /**
     * Busca una solicitud de crédito por el identificador externo del cliente.
     * @param clientId el identificador del cliente
     * @return un Optional con la solicitud más reciente del cliente si existe
     */
    Optional<CreditRequest> findTopByClientIdOrderByCreatedAtDesc(String clientId);

    /**
     * Verifica si existe una solicitud de crédito para el cliente given.
     * @param clientId el identificador del cliente
     * @return true si existe al menos una solicitud para el cliente
     */
    boolean existsByClientId(String clientId);

    /**
     * Actualiza el estado de una solicitud de crédito existente.
     * @param creditRequest la solicitud con el estado actualizado
     * @return la solicitud actualizada
     */
    CreditRequest update(CreditRequest creditRequest);
}

// === ARCHIVO: src/main/java/com/pragma/creditflow/domain/port/FraudEngineService.java ===
package com.pragma.creditflow.domain.port;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Puerto de integración con el motor antifraude externo.
 * Define el contrato para validar si una solicitud presenta indicadores de fraude.
 * La implementación concreta se encuentra en la capa de infraestructura.
 */
public interface FraudEngineService {

    /**
     * Resultado de la validación antifraude.
     */
    record FraudCheckResult(
        UUID requestId,
        boolean isFraudulent,
        String riskScore,
        String reason,
        LocalDateTime checkedAt
    ) {}

    /**
     * Ejecuta la validación antifraude para una solicitud de crédito.
     * @param requestId identificador de la solicitud
     * @param clientId identificador del cliente
     * @param amount monto solicitado
     * @param clientDocument documento de identidad del cliente
     * @param clientEmail correo electrónico del cliente
     * @return resultado de la validación con indicadores de fraude
     */
    FraudCheckResult checkFraud(
        UUID requestId,
        String clientId,
        BigDecimal amount,
        String clientDocument,
        String clientEmail
    );

    /**
     * Verifica la disponibilidad del servicio de antifraude.
     * @return true si el servicio está disponible
     */
    boolean isAvailable();

    /**
     * Obtiene el tiempo de respuesta promedio del servicio.
     * @return tiempo en milisegundos
     */
    long getAverageResponseTime();
}

// === ARCHIVO: src/main/java/com/pragma/creditflow/domain/port/RiskBureauService.java ===
package com.pragma.creditflow.domain.port;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Puerto de integración con el buró de riesgos externo.
 * Define el contrato para consultar el historial crediticio y límites del cliente.
 * La implementación concreta reside en la capa de infraestructura.
 */
public interface RiskBureauService {

    /**
     * Información del historial crediticio del cliente.
     */
    record CreditBureauInfo(
        String clientId,
        String document,
        BigDecimal creditScore,
        BigDecimal totalOutstandingDebt,
        BigDecimal availableCredit,
        int numberOfOpenCredits,
        int numberOfDelinquentCredits,
        LocalDateTime lastCheckedAt
    ) {}

    /**
     * Resultado de la evaluación de riesgo.
     */
    record RiskAssessmentResult(
        UUID requestId,
        boolean isApproved,
        BigDecimal approvedAmount,
        BigDecimal recommendedAmount,
        String reason,
        CreditBureauInfo bureauInfo,
        LocalDateTime assessedAt
    ) {}

    /**
     * Consulta el buró de riesgos para obtener el historial crediticio del cliente.
     * @param clientId identificador del cliente
     * @param clientDocument documento de identidad del cliente
     * @return información del historial crediticio
     */
    CreditBureauInfo getCreditHistory(String clientId, String clientDocument);

    /**
     * Evalúa el riesgo de aprobar una solicitud de crédito.
     * @param requestId identificador de la solicitud
     * @param clientId identificador del cliente
     * @param requestedAmount monto solicitado
     * @param clientDocument documento de identidad del cliente
     * @return resultado de la evaluación de riesgo
     */
    RiskAssessmentResult assessRisk(
        UUID requestId,
        String clientId,
        BigDecimal requestedAmount,
        String clientDocument
    );

    /**
     * Verifica la disponibilidad del servicio del buró de riesgos.
     * @return true si el servicio está disponible
     */
    boolean isAvailable();

    /**
     * Obtiene el tiempo de respuesta promedio del servicio.
     * @return tiempo en milisegundos
     */
    long getAverageResponseTime();
}

// === ARCHIVO: src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java ===
package com.pragma.creditflow.application.usecase;

import com.pragma.creditflow.domain.model.CreditRequest;
import com.pragma.creditflow.domain.model.CreditRequest.CreditRequestStatus;
import com.pragma.creditflow.domain.model.IdempotencyKey;
import com.pragma.creditflow.domain.port.CreditRequestRepository;
import com.pragma.creditflow.domain.port.FraudEngineService;
import com.pragma.creditflow.domain.port.IdempotencyRepository;
import com.pragma.creditflow.domain.port.RiskBureauService;
import com.pragma.creditflow.infrastructure.exception.ServiceUnavailableException;
import com.pragma.creditflow.infrastructure.exception.ValidationException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class CreditRequestUseCase {

    private static final Logger log = LoggerFactory.getLogger(CreditRequestUseCase.class);
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_WAIT_MS = 1000;

    private final CreditRequestRepository creditRequestRepository;
    private final IdempotencyRepository idempotencyRepository;
    private final FraudEngineService fraudEngineService;
    private final RiskBureauService riskBureauService;
    private final CircuitBreaker fraudCircuitBreaker;
    private final CircuitBreaker riskCircuitBreaker;
    private final Retry retryTemplate;

    public CreditRequestUseCase(
            CreditRequestRepository creditRequestRepository,
            IdempotencyRepository idempotencyRepository,
            FraudEngineService fraudEngineService,
            RiskBureauService riskBureauService,
            CircuitBreaker fraudCircuitBreaker,
            CircuitBreaker riskCircuitBreaker,
            RetryRegistry retryRegistry) {
        this.creditRequestRepository = creditRequestRepository;
        this.idempotencyRepository = idempotencyRepository;
        this.fraudEngineService = fraudEngineService;
        this.riskBureauService = riskBureauService;
        this.fraudCircuitBreaker = fraudCircuitBreaker;
        this.riskCircuitBreaker = riskCircuitBreaker;

        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(MAX_RETRIES)
                .waitDuration(Duration.ofMillis(RETRY_WAIT_MS))
                .retryExceptions(ServiceUnavailableException.class)
                .build();
        this.retryTemplate = retryRegistry.retry("creditRequestRetry", retryConfig);
    }

    public CreditRequest processCreditRequest(CreditRequest request, IdempotencyKey idempotencyKey) {
        log.info("Iniciando procesamiento de solicitud de crédito: {}", request.applicantId());

        validateIdempotency(idempotencyKey, request);

        CreditRequest savedRequest = creditRequestRepository.save(request);
        log.info("Solicitud de crédito guardada con ID: {}", savedRequest.id());

        CreditRequest validatedRequest = validateWithFraudEngine(savedRequest);
        validatedRequest = validateWithRiskBureau(validatedRequest);

        CreditRequest finalRequest = determineFinalStatus(validatedRequest);
        CreditRequest updatedRequest = creditRequestRepository.update(finalRequest);

        idempotencyRepository.save(idempotencyKey, updatedRequest.id());
        log.info("Solicitud de crédito procesada exitosamente. Estado final: {}", updatedRequest.status());

        return updatedRequest;
    }

    private void validateIdempotency(IdempotencyKey idempotencyKey, CreditRequest request) {
        Optional<UUID> existingRequestId = idempotencyRepository.findProcessedRequestId(idempotencyKey);
        if (existingRequestId.isPresent()) {
            log.warn("Solicitud duplicada detectada para clave de idempotencia: {}", idempotencyKey.getKey());
            CreditRequest existingRequest = creditRequestRepository.findById(existingRequestId.get());
            if (existingRequest.isPresent()) {
                throw new ValidationException("Solicitud ya procesada", "idempotency_key", existingRequest.get());
            }
        }
    }

    private CreditRequest validateWithFraudEngine(CreditRequest request) {
        log.info("Validando solicitud {} con motor antifraude", request.id());

        Supplier<Boolean> decoratedSupplier = CircuitBreaker.decorateSupplier(
                fraudCircuitBreaker,
                () -> fraudEngineService.checkFraud(request)
        );

        Supplier<Boolean> retryDecoratedSupplier = Retry.decorateSupplier(
                retryTemplate,
                decoratedSupplier
        );

        try {
            Boolean isFraudulent = retryDecoratedSupplier.get();
            if (Boolean.TRUE.equals(isFraudulent)) {
                log.warn("Solicitud {} marcada como fraudulenta por el motor antifraude", request.id());
                return request.withStatus(CreditRequestStatus.REJECTED_FRAUD);
            }
            log.info("Solicitud {} aprobada por motor antifraude", request.id());
            return request;
        } catch (Exception e) {
            log.error("Error al consultar motor antifraude para solicitud {}: {}", request.id(), e.getMessage());
            throw new ServiceUnavailableException("Fraud engine unavailable", e);
        }
    }

    private CreditRequest validateWithRiskBureau(CreditRequest request) {
        log.info("Validando solicitud {} con buró de riesgos", request.id());

        if (request.isRejected()) {
            log.info("Solicitud {} ya rechazada, omitiendo validación de buró de riesgos", request.id());
            return request;
        }

        Supplier<Boolean> decoratedSupplier = CircuitBreaker.decorateSupplier(
                riskCircuitBreaker,
                () -> riskBureauService.checkRisk(request)
        );

        Supplier<Boolean> retryDecoratedSupplier = Retry.decorateSupplier(
                retryTemplate,
                decoratedSupplier
        );

        try {
            Boolean hasRisk = retryDecoratedSupplier.get();
            if (Boolean.TRUE.equals(hasRisk)) {
                log.warn("Solicitud {} marcada como riesgosa por el buró de riesgos", request.id());
                return request.withStatus(CreditRequestStatus.REJECTED_RISK);
            }
            log.info("Solicitud {} aprobada por buró de riesgos", request.id());
            return request;
        } catch (Exception e) {
            log.error("Error al consultar buró de riesgos para solicitud {}: {}", request.id(), e.getMessage());
            throw new ServiceUnavailableException("Risk bureau unavailable", e);
        }
    }

    private CreditRequest determineFinalStatus(CreditRequest request) {
        if (request.isRejected()) {
            return request;
        }

        BigDecimal requestedAmount = request.requestedAmount();
        BigDecimal maxAllowedAmount = new BigDecimal("50000");

        if (requestedAmount.compareTo(maxAllowedAmount) > 0) {
            log.warn("Solicitud {} rechazada por monto exceder límite máximo: {} > {}",
                    request.id(), requestedAmount, maxAllowedAmount);
            return request.withStatus(CreditRequestStatus.REJECTED_AMOUNT);
        }

        log.info("Solicitud {} aprobada completamente", request.id());
        return request.withStatus(CreditRequestStatus.APPROVED);
    }

    public Optional<CreditRequest> getCreditRequestById(UUID requestId) {
        return creditRequestRepository.findById(requestId);
    }

    public Optional<CreditRequest> getCreditRequestByIdempotencyKey(IdempotencyKey idempotencyKey) {
        return idempotencyRepository.findProcessedRequestId(idempotencyKey)
                .flatMap(creditRequestRepository::findById);
    }
}

// === ARCHIVO: src/main/java/com/pragma/creditflow/infrastructure/adapter/IdempotencyAdapter.java ===
package com.pragma.creditflow.infrastructure.adapter;

import com.pragma.creditflow.domain.model.IdempotencyKey;
import com.pragma.creditflow.domain.port.IdempotencyRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class IdempotencyAdapter implements IdempotencyRepository {

    private static final Logger log = LoggerFactory.getLogger(IdempotencyAdapter.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UUID> findProcessedRequestId(IdempotencyKey idempotencyKey) {
        log.debug("Buscando solicitud procesada para clave de idempotencia: {}", idempotencyKey.getKey());

        try {
            IdempotencyEntity entity = entityManager.find(
                    IdempotencyEntity.class,
                    idempotencyKey.getKey()
            );

            if (entity != null) {
                log.debug("Encontrada solicitud procesada: {} para clave: {}",
                        entity.getRequestId(), idempotencyKey.getKey());
                return Optional.of(entity.getRequestId());
            }

            log.debug("No se encontró solicitud para clave de idempotencia: {}", idempotencyKey.getKey());
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error al buscar clave de idempotencia: {}", idempotencyKey.getKey(), e);
            return Optional.empty();
        }
    }

    @Override
    public void save(IdempotencyKey idempotencyKey, UUID requestId) {
        log.info("Guardando clave de idempotencia: {} para solicitud: {}",
                idempotencyKey.getKey(), requestId);

        try {
            IdempotencyEntity entity = new IdempotencyEntity();
            entity.setIdempotencyKey(idempotencyKey.getKey());
            entity.setRequestId(requestId);
            entity.setCreatedAt(java.time.Instant.now());

            entityManager.persist(entity);
            entityManager.flush();

            log.debug("Clave de idempotencia guardada exitosamente: {}", idempotencyKey.getKey());
        } catch (Exception e) {
            log.error("Error al guardar clave de idempotencia: {} para solicitud: {}",
                    idempotencyKey.getKey(), requestId, e);
            throw new RuntimeException("Failed to save idempotency key", e);
        }
    }

    @Entity
    @Table(name = "idempotency_keys")
    public static class IdempotencyEntity {

        @Id
        private String idempotencyKey;

        private UUID requestId;

        private java.time.Instant createdAt;

        public String getIdempotencyKey() {
            return idempotencyKey;
        }

        public void setIdempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
        }

        public UUID getRequestId() {
            return requestId;
        }

        public void setRequestId(UUID requestId) {
            this.requestId = requestId;
        }

        public java.time.Instant getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(java.time.Instant createdAt) {
            this.createdAt = createdAt;
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java ===
package com.pragma.creditflow.infrastructure.adapter;

import com.pragma.creditflow.domain.model.CreditRequest;
import com.pragma.creditflow.domain.model.CreditRequest.CreditRequestStatus;
import com.pragma.creditflow.domain.port.CreditRequestRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CreditRequestAdapter implements CreditRequestRepository {

    private static final Logger log = LoggerFactory.getLogger(CreditRequestAdapter.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CreditRequest save(CreditRequest request) {
        log.info("Guardando nueva solicitud de crédito para solicitante: {}", request.applicantId());

        try {
            CreditRequestEntity entity = toEntity(request);
            entity.setId(UUID.randomUUID());
            entity.setCreatedAt(Instant.now());
            entity.setUpdatedAt(Instant.now());

            entityManager.persist(entity);
            entityManager.flush();

            log.debug("Solicitud de crédito guardada con ID: {}", entity.getId());
            return toDomain(entity);
        } catch (Exception e) {
            log.error("Error al guardar solicitud de crédito para solicitante: {}", request.applicantId(), e);
            throw new RuntimeException("Failed to save credit request", e);
        }
    }

    @Override
    public CreditRequest update(CreditRequest request) {
        log.info("Actualizando solicitud de crédito con ID: {}", request.id());

        try {
            CreditRequestEntity entity = entityManager.find(CreditRequestEntity.class, request.id());

            if (entity == null) {
                log.error("No se encontró solicitud de crédito con ID: {}", request.id());
                throw new RuntimeException("Credit request not found: " + request.id());
            }

            entity.setStatus(request.status().name());
            entity.setApprovedAmount(request.approvedAmount());
            entity.setRejectionReason(request.rejectionReason());
            entity.setUpdatedAt(Instant.now());

            entityManager.merge(entity);
            entityManager.flush();

            log.debug("Solicitud de crédito actualizada con ID: {}", entity.getId());
            return toDomain(entity);
        } catch (Exception e) {
            log.error("Error al actualizar solicitud de crédito con ID: {}", request.id(), e);
            throw new RuntimeException("Failed to update credit request", e);
        }
    }

    @Override
    public Optional<CreditRequest> findById(UUID requestId) {
        log.debug("Buscando solicitud de crédito con ID: {}", requestId);

        try {
            CreditRequestEntity entity = entityManager.find(CreditRequestEntity.class, requestId);

            if (entity != null) {
                log.debug("Encontrada solicitud de crédito con ID: {}", requestId);
                return Optional.of(toDomain(entity));
            }

            log.debug("No se encontró solicitud de crédito con ID: {}", requestId);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error al buscar solicitud de crédito con ID: {}", requestId, e);
            return Optional.empty();
        }
    }

    private CreditRequestEntity toEntity(CreditRequest request) {
        CreditRequestEntity entity = new CreditRequestEntity();
        entity.setApplicantId(request.applicantId());
        entity.setRequestedAmount(request.requestedAmount());
        entity.setCreditPurpose(request.creditPurpose());
        entity.setTermMonths(request.termMonths());
        entity.setStatus(request.status().name());
        entity.setApprovedAmount(request.approvedAmount());
        entity.setRejectionReason(request.rejectionReason());
        return entity;
    }

    private CreditRequest toDomain(CreditRequestEntity entity) {
        return new CreditRequest(
                entity.getId(),
                entity.getApplicantId(),
                entity.getRequestedAmount(),
                entity.getCreditPurpose(),
                entity.getTermMonths(),
                CreditRequestStatus.valueOf(entity.getStatus()),
                entity.getApprovedAmount(),
                entity.getRejectionReason(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @Entity
    @Table(name = "credit_requests")
    public static class CreditRequestEntity {

        @Id
        private UUID id;

        private String applicantId;

        private BigDecimal requestedAmount;

        private String creditPurpose;

        private Integer termMonths;

        private String status;

        private BigDecimal approvedAmount;

        private String rejectionReason;

        private Instant createdAt;

        private Instant updatedAt;

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getApplicantId() {
            return applicantId;
        }

        public void setApplicantId(String applicantId) {
            this.applicantId = applicantId;
        }

        public BigDecimal getRequestedAmount() {
            return requestedAmount;
        }

        public void setRequestedAmount(BigDecimal requestedAmount) {
            this.requestedAmount = requestedAmount;
        }

        public String getCreditPurpose() {
            return creditPurpose;
        }

        public void setCreditPurpose(String creditPurpose) {
            this.creditPurpose = creditPurpose;
        }

        public Integer getTermMonths() {
            return termMonths;
        }

        public void setTermMonths(Integer termMonths) {
            this.termMonths = termMonths;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public BigDecimal getApprovedAmount() {
            return approvedAmount;
        }

        public void setApprovedAmount(BigDecimal approvedAmount) {
            this.approvedAmount = approvedAmount;
        }

        public String getRejectionReason() {
            return rejectionReason;
        }

        public void setRejectionReason(String rejectionReason) {
            this.rejectionReason = rejectionReason;
        }

        public Instant getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Instant createdAt) {
            this.createdAt = createdAt;
        }

        public Instant getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java ===
package com.pragma.creditflow.infrastructure.adapter;

import com.pragma.creditflow.domain.model.CreditRequest;
import com.pragma.creditflow.domain.port.FraudEngineService;
import com.pragma.creditflow.infrastructure.exception.ServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class FraudEngineAdapter implements FraudEngineService {

    private static final Logger log = LoggerFactory.getLogger(FraudEngineAdapter.class);

    private final RestTemplate restTemplate;
    private final String fraudEngineBaseUrl;
    private final int fraudEngineTimeout;

    public FraudEngineAdapter(
            RestTemplate restTemplate,
            @Value("${external-services.fraud-engine.base-url:http://localhost:8081}") String fraudEngineBaseUrl,
            @Value("${external-services.fraud-engine.timeout:5000}") int fraudEngineTimeout) {
        this.restTemplate = restTemplate;
        this.fraudEngineBaseUrl = fraudEngineBaseUrl;
        this.fraudEngineTimeout = fraudEngineTimeout;
    }

    @Override
    @CircuitBreaker(name = "fraudEngineCircuitBreaker", fallbackMethod = "fallbackCheckFraud")
    @Retry(name = "fraudEngineRetry")
    public boolean checkFraud(CreditRequest creditRequest) {
        log.info("Consultando motor antifraude para solicitud: {}", creditRequest.requestId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Request-ID", UUID.randomUUID().toString());

        Map<String, Object> requestBody = buildFraudRequestPayload(creditRequest);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            String url = fraudEngineBaseUrl + "/api/v1/fraud/check";
            Map<String, Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Map.class
            ).getBody();

            return parseFraudCheckResponse(response);
        } catch (Exception e) {
            log.error("Error al consultar motor antifraude: {}", e.getMessage());
            throw new ServiceUnavailableException("Fraud engine service unavailable: " + e.getMessage());
        }
    }

    private Map<String, Object> buildFraudRequestPayload(CreditRequest creditRequest) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("requestId", creditRequest.requestId().toString());
        payload.put("documentNumber", creditRequest.documentNumber());
        payload.put("amount", creditRequest.amount().doubleValue());
        payload.put("termMonths", creditRequest.termMonths());
        payload.put("requestedAt", creditRequest.createdAt().toString());
        return payload;
    }

    private boolean parseFraudCheckResponse(Map<String, Object> response) {
        if (response == null) {
            log.warn("Respuesta nula del motor antifraude, asumiendo no fraude por defecto");
            return false;
        }

        Object fraudDetected = response.get("fraudDetected");
        if (fraudDetected instanceof Boolean) {
            return (Boolean) fraudDetected;
        }

        String status = (String) response.get("status");
        return "APPROVED".equalsIgnoreCase(status);
    }

    private boolean fallbackCheckFraud(CreditRequest creditRequest, Exception e) {
        log.warn("Fallback ejecutado para motor antifraude. Solicitud: {}, Error: {}",
                creditRequest.requestId(), e.getMessage());
        return false;
    }
}

// === ARCHIVO: src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java ===
package com.pragma.creditflow.infrastructure.adapter;

import com.pragma.creditflow.domain.model.CreditRequest;
import com.pragma.creditflow.domain.port.RiskBureauService;
import com.pragma.creditflow.infrastructure.exception.ServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class RiskBureauAdapter implements RiskBureauService {

    private static final Logger log = LoggerFactory.getLogger(RiskBureauAdapter.class);

    private final RestTemplate restTemplate;
    private final String riskBureauBaseUrl;
    private final int riskBureauTimeout;
    private final BigDecimal defaultMaxCreditLimit;

    public RiskBureauAdapter(
            RestTemplate restTemplate,
            @Value("${external-services.risk-bureau.base-url:http://localhost:8082}") String riskBureauBaseUrl,
            @Value("${external-services.risk-bureau.timeout:5000}") int riskBureauTimeout,
            @Value("${credit.default-max-limit:100000}") BigDecimal defaultMaxCreditLimit) {
        this.restTemplate = restTemplate;
        this.riskBureauBaseUrl = riskBureauBaseUrl;
        this.riskBureauTimeout = riskBureauTimeout;
        this.defaultMaxCreditLimit = defaultMaxCreditLimit;
    }

    @Override
    @CircuitBreaker(name = "riskBureauCircuitBreaker", fallbackMethod = "fallbackCheckRisk")
    @Retry(name = "riskBureauRetry")
    public RiskBureauResult checkRisk(CreditRequest creditRequest) {
        log.info("Consultando buró de riesgos para documento: {}", creditRequest.documentNumber());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Request-ID", UUID.randomUUID().toString());

        Map<String, Object> requestBody = buildRiskRequestPayload(creditRequest);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            String url = riskBureauBaseUrl + "/api/v1/risk/evaluate";
            Map<String, Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Map.class
            ).getBody();

            return parseRiskResponse(response, creditRequest);
        } catch (Exception e) {
            log.error("Error al consultar buró de riesgos: {}", e.getMessage());
            throw new ServiceUnavailableException("Risk bureau service unavailable: " + e.getMessage());
        }
    }

    private Map<String, Object> buildRiskRequestPayload(CreditRequest creditRequest) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("documentNumber", creditRequest.documentNumber());
        payload.put("requestedAmount", creditRequest.amount().doubleValue());
        payload.put("requestedTerm", creditRequest.termMonths());
        payload.put("productType", creditRequest.creditType().name());
        return payload;
    }

    private RiskBureauResult parseRiskResponse(Map<String, Object> response, CreditRequest creditRequest) {
        if (response == null) {
            log.warn("Respuesta nula del buró de riesgos, usando valores por defecto");
            return new RiskBureauResult(true, defaultMaxCreditLimit, "DEFAULT");
        }

        Boolean approved = (Boolean) response.get("approved");
        BigDecimal maxCreditLimit = parseCreditLimit(response.get("maxCreditLimit"));
        String riskCategory = (String) response.get("riskCategory");

        if (approved == null) {
            approved = true;
        }
        if (maxCreditLimit == null) {
            maxCreditLimit = defaultMaxCreditLimit;
        }
        if (riskCategory == null) {
            riskCategory = "UNKNOWN";
        }

        return new RiskBureauResult(approved, maxCreditLimit, riskCategory);
    }

    private BigDecimal parseCreditLimit(Object creditLimit) {
        if (creditLimit == null) {
            return defaultMaxCreditLimit;
        }
        if (creditLimit instanceof Number) {
            return BigDecimal.valueOf(((Number) creditLimit).doubleValue());
        }
        try {
            return new BigDecimal(creditLimit.toString());
        } catch (NumberFormatException e) {
            log.warn("No se pudo parsear el límite de crédito: {}", creditLimit);
            return defaultMaxCreditLimit;
        }
    }

    private RiskBureauResult fallbackCheckRisk(CreditRequest creditRequest, Exception e) {
        log.warn("Fallback ejecutado para buró de riesgos. Solicitud: {}, Error: {}",
                creditRequest.requestId(), e.getMessage());
        return new RiskBureauResult(true, defaultMaxCreditLimit, "FALLBACK");
    }

    public record RiskBureauResult(boolean approved, BigDecimal maxCreditLimit, String riskCategory) {
    }
}

// === ARCHIVO: src/main/java/com/pragma/creditflow/infrastructure/config/ResilienceConfig.java ===
package com.pragma.creditflow.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();

        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .build();

        return RetryRegistry.of(config);
    }

    @Bean
    public TimeLimiterRegistry timeLimiterRegistry() {
        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(5))
                .build();

        return TimeLimiterRegistry.of(config);
    }
}

// === ARCHIVO: src/main/java/com/pragma/creditflow/infrastructure/exception/CustomException.java ===
package com.pragma.creditflow.infrastructure.exception;

import java.time.Instant;
import java.util.UUID;

public class CustomException extends RuntimeException {
    private final String errorCode;
    private final Instant timestamp;
    private final String details;
    private final UUID requestId;

    public CustomException(String message, String errorCode) {
        super(message);
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("El mensaje de error no puede ser nulo o vacío");
        }
        if (errorCode == null || errorCode.isBlank()) {
            throw new IllegalArgumentException("El código de error no puede ser nulo o vacío");
        }
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
        this.details = null;
        this.requestId = UUID.randomUUID();
    }

    public CustomException(String message, String errorCode, String details) {
        this(message, errorCode);
        this.details = details;
    }

    public CustomException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("El mensaje de error no puede ser nulo o vacío");
        }
        if (errorCode == null || errorCode.isBlank()) {
            throw new IllegalArgumentException("El código de error no puede ser nulo o vacío");
        }
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
        this.details = cause != null ? cause.getMessage() : null;
        this.requestId = UUID.randomUUID();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public static CustomException of(String message, String errorCode) {
        return new CustomException(message, errorCode);
    }

    public static CustomException withDetails(String message, String errorCode, String details) {
        return new CustomException(message, errorCode, details);
    }

    public static CustomException withCause(String message, String errorCode, Throwable cause) {
        return new CustomException(message, errorCode, cause);
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "errorCode='" + errorCode + '\'' +
                ", timestamp=" + timestamp +
                ", details='" + details + '\'' +
                ", requestId=" + requestId +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}

// === ARCHIVO: src/main/java/com/pragma/creditflow/infrastructure/exception/ValidationException.java ===
package com.pragma.creditflow.infrastructure.exception;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ValidationException extends CustomException {
    private final String fieldName;
    private final Object rejectedValue;
    private final String validationRule;
    private final List<ValidationError> nestedErrors;

    public static class ValidationError {
        private final String field;
        private final String message;
        private final Object rejectedValue;
        private final String code;

        public ValidationError(String field, String message, Object rejectedValue, String code) {
            this.field = field;
            this.message = message;
            this.rejectedValue = rejectedValue;
            this.code = code;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }

        public Object getRejectedValue() {
            return rejectedValue;
        }

        public String getCode() {
            return code;
        }
    }

    public ValidationException(String message, String fieldName, Object rejectedValue, String validationRule) {
        super(message, "VALIDATION_ERROR");
        if (fieldName == null || fieldName.isBlank()) {
            throw new IllegalArgumentException("El nombre del campo no puede ser nulo o vacío");
        }
        this.fieldName = fieldName;
        this.rejectedValue = rejectedValue;
        this.validationRule = validationRule;
        this.nestedErrors = new ArrayList<>();
    }

    public ValidationException(String message, String fieldName, Object rejectedValue) {
        this(message, fieldName, rejectedValue, null);
    }

    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR");
        this.fieldName = null;
        this.rejectedValue = null;
        this.validationRule = null;
        this.nestedErrors = new ArrayList<>();
    }

    public ValidationException(String message, List<ValidationError> errors) {
        super(message, "VALIDATION_ERROR");
        this.fieldName = null;
        this.rejectedValue = null;
        this.validationRule = null;
        this.nestedErrors = errors != null ? new ArrayList<>(errors) : new ArrayList<>();
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public String getValidationRule() {
        return validationRule;
    }

    public List<ValidationError> getNestedErrors() {
        return Collections.unmodifiableList(nestedErrors);
    }

    public ValidationException withNestedError(String field, String message, Object rejectedValue, String code) {
        this.nestedErrors.add(new ValidationError(field, message, rejectedValue, code));
        return this;
    }

    public boolean hasNestedErrors() {
        return !nestedErrors.isEmpty();
    }

    public static ValidationException forField(String fieldName, Object rejectedValue, String rule) {
        String message = String.format("Validación fallida para el campo '%s' con valor '%s'", fieldName, rejectedValue);
        return new ValidationException(message, fieldName, rejectedValue, rule);
    }

    public static ValidationException required(String fieldName) {
        String message = String.format("El campo '%s' es obligatorio", fieldName);
        return new ValidationException(message, fieldName, null, "NOT_NULL");
    }

    public static ValidationException invalidFormat(String fieldName, Object rejectedValue) {
        String message = String.format("El campo '%s' tiene un formato inválido: %s", fieldName, rejectedValue);
        return new ValidationException(message, fieldName, rejectedValue, "INVALID_FORMAT");
    }

    @Override
    public String toString() {
        return "ValidationException{" +
                "fieldName='" + fieldName + '\'' +
                ", rejectedValue=" + rejectedValue +
                ", validationRule='" + validationRule + '\'' +
                ", nestedErrorsCount=" + nestedErrors.size() +
                ", errorCode='" + getErrorCode() + '\'' +
                ", requestId=" + getRequestId() +
                '}';
    }
}

// === ARCHIVO: src/main/java/com/pragma/creditflow/infrastructure/exception/GlobalExceptionHandler.java ===
package com.pragma.creditflow.infrastructure.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public record ErrorResponse(
        String errorCode,
        String message,
        Instant timestamp,
        UUID requestId,
        Map<String, Object> details
    ) {}

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex, WebRequest request) {
        logger.error("Excepción personalizada procesada: {}, Código: {}, RequestId: {}",
                ex.getMessage(), ex.getErrorCode(), ex.getRequestId());

        Map<String, Object> details = new HashMap<>();
        if (ex.getDetails() != null) {
            details.put("details", ex.getDetails());
        }

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getTimestamp(),
                ex.getRequestId(),
                details.isEmpty() ? null : details
        );

        HttpStatus status = determineHttpStatus(ex.getErrorCode());
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, WebRequest request) {
        logger.warn("Error de validación: {}, Campo: {}, RequestId: {}",
                ex.getMessage(), ex.getFieldName(), ex.getRequestId());

        Map<String, Object> details = new HashMap<>();

        if (ex.getFieldName() != null) {
            details.put("field", ex.getFieldName());
        }
        if (ex.getRejectedValue() != null) {
            details.put("rejectedValue", ex.getRejectedValue());
        }
        if (ex.getValidationRule() != null) {
            details.put("validationRule", ex.getValidationRule());
        }
        if (ex.hasNestedErrors()) {
            List<Map<String, Object>> nestedErrorsList = ex.getNestedErrors().stream()
                    .map(error -> {
                        Map<String, Object> errorMap = new HashMap<>();
                        errorMap.put("field", error.getField());
                        errorMap.put("message", error.getMessage());
                        errorMap.put("rejectedValue", error.getRejectedValue());
                        errorMap.put("code", error.getCode());
                        return errorMap;
                    })
                    .collect(Collectors.toList());
            details.put("errors", nestedErrorsList);
        }

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getTimestamp(),
                ex.getRequestId(),
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, WebRequest request) {

        logger.warn("Validación de argumentos fallida: {} errores", ex.getBindingResult().getErrorCount());

        List<Map<String, Object>> fieldErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    Map<String, Object> errorMap = new HashMap<>();
                    if (error instanceof FieldError fieldError) {
                        errorMap.put("field", fieldError.getField());
                        errorMap.put("rejectedValue", fieldError.getRejectedValue());
                    } else {
                        errorMap.put("field", error.getObjectName());
                    }
                    errorMap.put("message", error.getDefaultMessage());
                    errorMap.put("code", error.getCode());
                    return errorMap;
                })
                .collect(Collectors.toList());

        Map<String, Object> details = new HashMap<>();
        details.put("errors", fieldErrors);

        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_ERROR",
                "Los datos de la solicitud no son válidos",
                Instant.now(),
                UUID.randomUUID(),
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleServiceUnavailableException(
            ServiceUnavailableException ex, WebRequest request) {

        logger.error("Servicio externo no disponible: {}, Servicio: {}, RequestId: {}",
                ex.getMessage(), ex.getServiceName(), ex.getRequestId());

        Map<String, Object> details = new HashMap<>();
        details.put("serviceName", ex.getServiceName());
        if (ex.getRetryAfter() != null) {
            details.put("retryAfter", ex.getRetryAfter());
        }

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getTimestamp(),
                ex.getRequestId(),
                details
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        logger.warn("Argumento ilegal: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "ILLEGAL_ARGUMENT",
                ex.getMessage(),
                Instant.now(),
                UUID.randomUUID(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        logger.error("Excepción no manejada: ", ex);

        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_ERROR",
                "Ha ocurrido un error interno en el sistema",
                Instant.now(),
                UUID.randomUUID(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private HttpStatus determineHttpStatus(String errorCode) {
        if (errorCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return switch (errorCode) {
            case "NOT_FOUND" -> HttpStatus.NOT_FOUND;
            case "VALIDATION_ERROR" -> HttpStatus.BAD_REQUEST;
            case "CONFLICT" -> HttpStatus.CONFLICT;
            case "FORBIDDEN" -> HttpStatus.FORBIDDEN;
            case "UNAUTHORIZED" -> HttpStatus.UNAUTHORIZED;
            case "SERVICE_UNAVAILABLE" -> HttpStatus.SERVICE_UNAVAILABLE;
            case "TIMEOUT" -> HttpStatus.GATEWAY_TIMEOUT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}

// === ARCHIVO: src/main/java/com/ragma/creditflow/infrastructure/exception/ServiceUnavailableException.java ===
package com.pragma.creditflow.infrastructure.exception;


import com.pragma.creditflow.CircuitBreaker;
public class ServiceUnavailableException extends RuntimeException {
    private final String serviceName;
    private final int retryCount;
    private final long retryDelayMs;
    private final boolean circuitBreakerOpen;
    
    public ServiceUnavailableException(String serviceName) {
        super(buildMessage(serviceName, 0, 0, false));
        this.serviceName = serviceName;
        this.retryCount = 0;
        this.retryDelayMs = 0;
        this.circuitBreakerOpen = false;
    }
    
    public ServiceUnavailableException(String serviceName, int retryCount) {
        super(buildMessage(serviceName, retryCount, 0, false));
        this.serviceName = serviceName;
        this.retryCount = retryCount;
        this.retryDelayMs = 0;
        this.circuitBreakerOpen = false;
    }
    
    public ServiceUnavailableException(String serviceName, int retryCount, long retryDelayMs) {
        super(buildMessage(serviceName, retryCount, retryDelayMs, false));
        this.serviceName = serviceName;
        this.retryCount = retryCount;
        this.retryDelayMs = retryDelayMs;
        this.circuitBreakerOpen = false;
    }
    
    public ServiceUnavailableException(String serviceName, int retryCount, long retryDelayMs, boolean circuitBreakerOpen) {
        super(buildMessage(serviceName, retryCount, retryDelayMs, circuitBreakerOpen));
        this.serviceName = serviceName;
        this.retryCount = retryCount;
        this.retryDelayMs = retryDelayMs;
        this.circuitBreakerOpen = circuitBreakerOpen;
    }
    
    public ServiceUnavailableException(String serviceName, Throwable cause) {
        super(buildMessage(serviceName, 0, 0, false), cause);
        this.serviceName = serviceName;
        this.retryCount = 0;
        this.retryDelayMs = 0;
        this.circuitBreakerOpen = false;
    }
    
    public ServiceUnavailableException(String serviceName, int retryCount, Throwable cause) {
        super(buildMessage(serviceName, retryCount, 0, false), cause);
        this.serviceName = serviceName;
        this.retryCount = retryCount;
        this.retryDelayMs = 0;
        this.circuitBreakerOpen = false;
    }
    
    public ServiceUnavailableException(String serviceName, int retryCount, long retryDelayMs, Throwable cause) {
        super(buildMessage(serviceName, retryCount, retryDelayMs, false), cause);
        this.serviceName = serviceName;
        this.retryCount = retryCount;
        this.retryDelayMs = retryDelayMs;
        this.circuitBreakerOpen = false;
    }
    
    public ServiceUnavailableException(String serviceName, int retryCount, long retryDelayMs, boolean circuitBreakerOpen, Throwable cause) {
        super(buildMessage(serviceName, retryCount, retryDelayMs, circuitBreakerOpen), cause);
        this.serviceName = serviceName;
        this.retryCount = retryCount;
        this.retryDelayMs = retryDelayMs;
        this.circuitBreakerOpen = circuitBreakerOpen;
    }
    
    private static String buildMessage(String serviceName, int retryCount, long retryDelayMs, boolean circuitBreakerOpen) {
        StringBuilder message = new StringBuilder();
        message.append("El servicio externo '");
        message.append(serviceName);
        message.append("' no esta disponible");
        
        if (retryCount > 0) {
            message.append(". Reintentos intentados: ");
            message.append(retryCount);
        }
        
        if (retryDelayMs > 0) {
            message.append(". Delay entre reintentos: ");
            message.append(retryDelayMs);
            message.append("ms");
        }
        
        if (circuitBreakerOpen) {
            message.append(". CircuitBreaker ABIERTO - el servicio esta temporalmente no disponible");
        }
        
        return message.toString();
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public int getRetryCount() {
        return retryCount;
    }
    
    public long getRetryDelayMs() {
        return retryDelayMs;
    }
    
    public boolean isCircuitBreakerOpen() {
        return circuitBreakerOpen;
    }
    
    public boolean hasRetries() {
        return retryCount > 0;
    }
    
    public boolean isTimeoutError() {
        return getCause() instanceof java.util.concurrent.TimeoutException;
    }
    
    public boolean isConnectionError() {
        Throwable cause = getCause();
        if (cause == null) {
            return false;
        }
        return cause instanceof java.io.IOException || 
               cause instanceof java.net.UnknownHostException ||
               cause instanceof java.net.ConnectException;
    }
    
    public String getDetailedMessage() {
        StringBuilder details = new StringBuilder();
        details.append("ServiceUnavailableException{\n");
        details.append("  serviceName: ").append(serviceName).append("\n");
        details.append("  retryCount: ").append(retryCount).append("\n");
        details.append("  retryDelayMs: ").append(retryDelayMs).append("\n");
        details.append("  circuitBreakerOpen: ").append(circuitBreakerOpen).append("\n");
        details.append("  message: ").append(getMessage()).append("\n");
        
        if (getCause() != null) {
            details.append("  cause: ").append(getCause().getClass().getName());
            details.append(": ").append(getCause().getMessage()).append("\n");
        }
        
        details.append("}");
        return details.toString();
    }
}

// === ARCHIVO: src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java ===
package com.pragma.creditflow.application.usecase;

import com.pragma.creditflow.domain.model.CreditRequest;
import com.pragma.creditflow.domain.model.CreditRequest.CreditRequestStatus;
import com.pragma.creditflow.domain.model.IdempotencyKey;
import com.pragma.creditflow.domain.port.CreditRequestRepository;
import com.pragma.creditflow.domain.port.FraudEngineService;
import com.pragma.creditflow.domain.port.IdempotencyRepository;
import com.pragma.creditflow.domain.port.RiskBureauService;
import com.pragma.creditflow.infrastructure.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreditRequestUseCaseTest - Pruebas unitarias del caso de uso principal")
class CreditRequestUseCaseTest {

    @Mock
    private CreditRequestRepository creditRequestRepository;

    @Mock
    private IdempotencyRepository idempotencyRepository;

    @Mock
    private FraudEngineService fraudEngineService;

    @Mock
    private RiskBureauService riskBureauService;

    private CreditRequestUseCase creditRequestUseCase;

    @BeforeEach
    void setUp() {
        creditRequestUseCase = new CreditRequestUseCase(
                creditRequestRepository,
                idempotencyRepository,
                fraudEngineService,
                riskBureauService
        );
    }

    @Nested
    @DisplayName("Escenario: Validación de idempotencia")
    class IdempotencyValidation {

        @Test
        @DisplayName("Debe rechazar solicitud duplicada cuando la clave de idempotencia ya fue procesada")
        void mustRejectDuplicateRequestWhenIdempotencyKeyAlreadyProcessed() {
            String idempotencyKeyValue = "test-key-123";
            IdempotencyKey idempotencyKey = IdempotencyKey.of(idempotencyKeyValue);
            UUID existingRequestId = UUID.randomUUID();

            when(idempotencyRepository.findProcessedRequestId(idempotencyKey))
                    .thenReturn(Optional.of(existingRequestId));

            CreditRequest newRequest = new CreditRequest(
                    UUID.randomUUID(),
                    "client-001",
                    new BigDecimal("10000.00"),
                    12,
                    CreditRequestStatus.PENDING,
                    null,
                    null
            );

            ValidationException exception = assertThrows(
                    ValidationException.class,
                    () -> creditRequestUseCase.processRequest(newRequest, idempotencyKeyValue)
            );

            assertEquals("DUPLICATE_REQUEST", exception.getCode());
            assertTrue(exception.getMessage().contains("ya fue procesada"));

            verify(idempotencyRepository).findProcessedRequestId(idempotencyKey);
            verify(creditRequestRepository, never()).save(any());
            verify(fraudEngineService, never()).checkFraud(any());
            verify(riskBureauService, never()).evaluateRisk(any());
        }

        @Test
        @DisplayName("Debe continuar con el flujo cuando la clave de idempotencia es nueva")
        void mustContinueFlowWhenIdempotencyKeyIsNew() {
            String idempotencyKeyValue = "new-key-456";
            IdempotencyKey idempotencyKey = IdempotencyKey.of(idempotencyKeyValue);

            when(idempotencyRepository.findProcessedRequestId(idempotencyKey))
                    .thenReturn(Optional.empty());
            when(fraudEngineService.checkFraud(any())).thenReturn(false);
            when(riskBureauService.evaluateRisk(any())).thenReturn(true);
            when(creditRequestRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            CreditRequest request = new CreditRequest(
                    UUID.randomUUID(),
                    "client-002",
                    new BigDecimal("5000.00"),
                    6,
                    CreditRequestStatus.PENDING,
                    null,
                    null
            );

            CreditRequest result = creditRequestUseCase.processRequest(request, idempotencyKeyValue);

            assertNotNull(result);
            verify(idempotencyRepository).findProcessedRequestId(idempotencyKey);
            verify(creditRequestRepository).save(any());
        }
    }

    @Nested
    @DisplayName("Escenario: Validación de datos de entrada")
    class InputValidation {

        @Test
        @DisplayName("Debe rechazar solicitud con monto negativo")
        void mustRejectRequestWithNegativeAmount() {
            CreditRequest request = new CreditRequest(
                    UUID.randomUUID(),
                    "client-003",
                    new BigDecimal("-1000.00"),
                    12,
                    CreditRequestStatus.PENDING,
                    null,
                    null
            );

            ValidationException exception = assertThrows(
                    ValidationException.class,
                    () -> creditRequestUseCase.processRequest(request, "key-789")
            );

            assertEquals("INVALID_AMOUNT", exception.getCode());
        }

        @Test
        @DisplayName("Debe rechazar solicitud con plazo mayor a 60 meses")
        void mustRejectRequestWithTermExceedingLimit() {
            CreditRequest request = new CreditRequest(
                    UUID.randomUUID(),
                    "client-004",
                    new BigDecimal("10000.00"),
                    72,
                    CreditRequestStatus.PENDING,
                    null,
                    null
            );

            ValidationException exception = assertThrows(
                    ValidationException.class,
                    () -> creditRequestUseCase.processRequest(request, "key-101")
            );

            assertEquals("INVALID_TERM", exception.getCode());
        }

        @Test
        @DisplayName("Debe rechazar solicitud con cliente nulo")
        void mustRejectRequestWithNullClient() {
            CreditRequest request = new CreditRequest(
                    UUID.randomUUID(),
                    null,
                    new BigDecimal("10000.00"),
                    12,
                    CreditRequestStatus.PENDING,
                    null,
                    null
            );

            ValidationException exception = assertThrows(
                    ValidationException.class,
                    () -> creditRequestUseCase.processRequest(request, "key-102")
            );

            assertEquals("INVALID_CLIENT", exception.getCode());
        }
    }

    @Nested
    @DisplayName("Escenario: Integración con servicios externos")
    class ExternalServicesIntegration {

        @Test
        @DisplayName("Debe aprobar solicitud cuando pasa todas las validaciones")
        void mustApproveRequestWhenAllValidationsPass() {
            when(idempotencyRepository.findProcessedRequestId(any())).thenReturn(Optional.empty());
            when(fraudEngineService.checkFraud(any())).thenReturn(false);
            when(riskBureauService.evaluateRisk(any())).thenReturn(true);
            when(creditRequestRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            CreditRequest request = new CreditRequest(
                    UUID.randomUUID(),
                    "client-005",
                    new BigDecimal("15000.00"),
                    24,
                    CreditRequestStatus.PENDING,
                    null,
                    null
            );

            CreditRequest result = creditRequestUseCase.processRequest(request, "key-approve");

            assertEquals(CreditRequestStatus.APPROVED, result.status());
            verify(fraudEngineService).checkFraud(any());
            verify(riskBureauService).evaluateRisk(any());
        }

        @Test
        @DisplayName("Debe rechazar solicitud cuando el motor antifraude detecta fraude")
        void mustRejectRequestWhenFraudEngineDetectsFraud() {
            when(idempotencyRepository.findProcessedRequestId(any())).thenReturn(Optional.empty());
            when(fraudEngineService.checkFraud(any())).thenReturn(true);
            when(creditRequestRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            CreditRequest request = new CreditRequest(
                    UUID.randomUUID(),
                    "client-006",
                    new BigDecimal("8000.00"),
                    12,
                    CreditRequestStatus.PENDING,
                    null,
                    null
            );

            CreditRequest result = creditRequestUseCase.processRequest(request, "key-fraud");

            assertEquals(CreditRequestStatus.REJECTED, result.status());
            assertTrue(result.rejectionReason().contains("fraude") || 
                       result.rejectionReason().contains("fraud"));
            verify(riskBureauService, never()).evaluateRisk(any());
        }

        @Test
        @DisplayName("Debe rechazar solicitud cuando el buró de riesgos excede el límite")
        void mustRejectRequestWhenRiskBureauExceedsLimit() {
            when(idempotencyRepository.findProcessedRequestId(any())).thenReturn(Optional.empty());
            when(fraudEngineService.checkFraud(any())).thenReturn(false);
            when(riskBureauService.evaluateRisk(any())).thenReturn(false);
            when(creditRequestRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            CreditRequest request = new CreditRequest(
                    UUID.randomUUID(),
                    "client-007",
                    new BigDecimal("20000.00"),
                    36,
                    CreditRequestStatus.PENDING,
                    null,
                    null
            );

            CreditRequest result = creditRequestUseCase.processRequest(request, "key-risk");

            assertEquals(CreditRequestStatus.REJECTED, result.status());
            assertTrue(result.rejectionReason().contains("riesgo") || 
                       result.rejectionReason().contains("risk"));
        }
    }

    @Nested
    @DisplayName("Escenario: Persistencia de clave de idempotencia")
    class IdempotencyKeyPersistence {

        @Test
        @DisplayName("Debe guardar la clave de idempotencia después de procesar exitosamente")
        void mustSaveIdempotencyKeyAfterSuccessfulProcessing() {
            String idempotencyKeyValue = "final-key-999";
            UUID requestId = UUID.randomUUID();

            when(idempotencyRepository.findProcessedRequestId(any())).thenReturn(Optional.empty());
            when(fraudEngineService.checkFraud(any())).thenReturn(false);
            when(riskBureauService.evaluateRisk(any())).thenReturn(true);
            when(creditRequestRepository.save(any())).thenAnswer(inv -> {
                CreditRequest req = inv.getArgument(0);
                return new CreditRequest(
                        requestId,
                        req.clientId(),
                        req.amount(),
                        req.termMonths(),
                        req.status(),
                        req.approvedAt(),
                        req.rejectionReason()
                );
            });

            CreditRequest request = new CreditRequest(
                    UUID.randomUUID(),
                    "client-008",
                    new BigDecimal("5000.00"),
                    6,
                    CreditRequestStatus.PENDING,
                    null,
                    null
            );

            creditRequestUseCase.processRequest(request, idempotencyKeyValue);

            ArgumentCaptor<IdempotencyKey> keyCaptor = ArgumentCaptor.forClass(IdempotencyKey.class);
            ArgumentCaptor<UUID> requestIdCaptor = ArgumentCaptor.forClass(UUID.class);

            verify(idempotencyRepository).save(keyCaptor.capture(), requestIdCaptor.capture());

            assertEquals(idempotencyKeyValue, keyCaptor.getValue().getKey());
            assertEquals(requestId, requestIdCaptor.getValue());
        }

        @Test
        @DisplayName("No debe guardar clave de idempotencia cuando la solicitud es duplicada")
        void mustNotSaveIdempotencyKeyWhenRequestIsDuplicate() {
            String idempotencyKeyValue = "duplicate-key-111";
            UUID existingRequestId = UUID.randomUUID();

            when(idempotencyRepository.findProcessedRequestId(any()))
                    .thenReturn(Optional.of(existingRequestId));

            CreditRequest request = new CreditRequest(
                    UUID.randomUUID(),
                    "client-009",
                    new BigDecimal("3000.00"),
                    3,
                    CreditRequestStatus.PENDING,
                    null,
                    null
            );

            assertThrows(ValidationException.class, 
                    () -> creditRequestUseCase.processRequest(request, idempotencyKeyValue));

            verify(idempotencyRepository, never()).save(any(), any());
        }
    }
}

// === ARCHIVO: src/test/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapterTest.java ===
package com.pragma.creditflow.infrastructure.adapter;


import com.pragma.creditflow.FraudEngine;
import com.pragma.creditflow.domain.model.CreditRequest;
import com.pragma.creditflow.domain.model.CreditRequest.CreditRequestStatus;
import com.pragma.creditflow.domain.port.FraudEngineService;
import com.pragma.creditflow.infrastructure.exception.ServiceUnavailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FraudEngineAdapterTest - Pruebas unitarias del adaptador del motor antifraude")
class FraudEngineAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    private FraudEngineAdapter fraudEngineAdapter;

    private static final String FRAUD_SERVICE_URL = "http://fraud-engine-api/internal/check";

    @BeforeEach
    void setUp() {
        fraudEngineAdapter = new FraudEngineAdapter(restTemplate);
    }

    @Nested
    @DisplayName("Escenario: Verificación de fraude exitosa")
    class FraudCheckSuccess {

        @Test
        @DisplayName("Debe retornar false cuando el motor antifraude indica que no hay fraude")
        void mustReturnFalseWhenFraudEngineIndicatesNoFraud() {
            CreditRequest request = createTestRequest("client-100", new BigDecimal("5000.00"));
            FraudEngineAdapter.FraudCheckResponse response = 
                    new FraudEngineAdapter.FraudCheckResponse(false, 0.15f);

            when(restTemplate.postForEntity(
                    eq(FRAUD_SERVICE_URL),
                    any(FraudEngineAdapter.FraudCheckRequest.class),
                    eq(FraudEngineAdapter.FraudCheckResponse.class)
            )).thenReturn(ResponseEntity.ok(response));

            boolean result = fraudEngineAdapter.checkFraud(request);

            assertFalse(result);
            verify(restTemplate).postForEntity(
                    eq(FRAUD_SERVICE_URL),
                    any(FraudEngineAdapter.FraudCheckRequest.class),
                    eq(FraudEngineAdapter.FraudCheckResponse.class)
            );
        }

        @Test
        @DisplayName("Debe retornar true cuando el motor antifraude detecta fraude")
        void mustReturnTrueWhenFraudEngineDetectsFraud() {
            CreditRequest request = createTestRequest("client-101", new BigDecimal("15000.00"));
            FraudEngineAdapter.FraudCheckResponse response = 
                    new FraudEngineAdapter.FraudCheckResponse(true, 0.85f);

            when(restTemplate.postForEntity(
                    eq(FRAUD_SERVICE_URL),
                    any(FraudEngineAdapter.FraudCheckRequest.class),
                    eq(FraudEngineAdapter.FraudCheckResponse.class)
            )).thenReturn(ResponseEntity.ok(response));

            boolean result = fraudEngineAdapter.checkFraud(request);

            assertTrue(result);
        }

        @Test
        @DisplayName("Debe enviar el clientId correcto al motor antifraude")
        void mustSendCorrectClientIdToFraudEngine() {
            String expectedClientId = "client-102-test";
            CreditRequest request = createTestRequest(expectedClientId, new BigDecimal("8000.00"));
            FraudEngineAdapter.FraudCheckResponse response = 
                    new FraudEngineAdapter.FraudCheckResponse(false, 0.1f);

            when(restTemplate.postForEntity(
                    eq(FRAUD_SERVICE_URL),
                    any(FraudEngineAdapter.FraudCheckRequest.class),
                    eq(FraudEngineAdapter.FraudCheckResponse.class)
            )).thenReturn(ResponseEntity.ok(response));

            fraudEngineAdapter.checkFraud(request);

            verify(restTemplate).postForEntity(
                    eq(FRAUD_SERVICE_URL),
                    argThat(req -> req instanceof FraudEngineAdapter.FraudCheckRequest &&
                            ((FraudEngineAdapter.FraudCheckRequest) req).clientId().equals(expectedClientId)),
                    eq(FraudEngineAdapter.FraudCheckResponse.class)
            );
        }

        @Test
        @DisplayName("Debe enviar el monto correcto al motor antifraude")
        void mustSendCorrectAmountToFraudEngine() {
            BigDecimal expectedAmount = new BigDecimal("12000.50");
            CreditRequest request = createTestRequest("client-103", expectedAmount);
            FraudEngineAdapter.FraudCheckResponse response = 
                    new FraudEngineAdapter.FraudCheckResponse(false, 0.05f);

            when(restTemplate.postForEntity(
                    eq(FRAUD_SERVICE_URL),
                    any(FraudEngineAdapter.FraudCheckRequest.class),
                    eq(FraudEngineAdapter.FraudCheckResponse.class)
            )).thenReturn(ResponseEntity.ok(response));

            fraudEngineAdapter.checkFraud(request);

            verify(restTemplate).postForEntity(
                    eq(FRAUD_SERVICE_URL),
                    argThat(req -> req instanceof FraudEngineAdapter.FraudCheckRequest &&
                            ((FraudEngineAdapter.FraudCheckRequest) req).amount()
                                    .compareTo(expectedAmount) == 0),
                    eq(FraudEngineAdapter.FraudCheckResponse.class)
            );
        }
    }

    @Nested
    @DisplayName("Escenario: Fallo del servicio externo")
    class ExternalServiceFailure {

        @Test
        @DisplayName("Debe lanzar ServiceUnavailableException cuando el servicio no responde")
        void mustThrowServiceUnavailableExceptionWhenServiceDoesNotRespond() {
            CreditRequest request = createTestRequest("client-104", new BigDecimal("5000.00"));

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenThrow(new RestClientException("Connection refused"));

            ServiceUnavailableException exception = assertThrows(
                    ServiceUnavailableException.class,
                    () -> fraudEngineAdapter.checkFraud(request)
            );

            assertTrue(exception.getMessage().contains("fraud") ||
                       exception.getMessage().contains("FraudEngine"));
        }

        @Test
        @DisplayName("Debe lanzar ServiceUnavailableException cuando el servicio retorna error HTTP")
        void mustThrowServiceUnavailableExceptionWhenServiceReturnsHttpError() {
            CreditRequest request = createTestRequest("client-105", new BigDecimal("7000.00"));

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenReturn(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());

            ServiceUnavailableException exception = assertThrows(
                    ServiceUnavailableException.class,
                    () -> fraudEngineAdapter.checkFraud(request)
            );

            assertEquals("SERVICE_UNAVAILABLE", exception.getCode());
        }

        @Test
        @DisplayName("Debe lanzar ServiceUnavailableException cuando hay timeout")
        void mustThrowServiceUnavailableExceptionOnTimeout() {
            CreditRequest request = createTestRequest("client-106", new BigDecimal("9000.00"));

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenThrow(new RestClientException("Read timed out"));

            ServiceUnavailableException exception = assertThrows(
                    ServiceUnavailableException.class,
                    () -> fraudEngineAdapter.checkFraud(request)
            );

            assertTrue(exception.getMessage().toLowerCase().contains("timeout") ||
                       exception.getMessage().toLowerCase().contains("service"));
        }
    }

    @Nested
    @DisplayName("Escenario: Manejo de respuestas nulas")
    class NullResponseHandling {

        @Test
        @DisplayName("Debe retornar false cuando la respuesta es null")
        void mustReturnFalseWhenResponseIsNull() {
            CreditRequest request = createTestRequest("client-107", new BigDecimal("4000.00"));

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenReturn(ResponseEntity.ok(null));

            boolean result = fraudEngineAdapter.checkFraud(request);

            assertFalse(result);
        }

        @Test
        @DisplayName("Debe manejar respuesta con score de riesgo null")
        void mustHandleResponseWithNullRiskScore() {
            CreditRequest request = createTestRequest("client-108", new BigDecimal("6000.00"));
            FraudEngineAdapter.FraudCheckResponse response = 
                    new FraudEngineAdapter.FraudCheckResponse(false, null);

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenReturn(ResponseEntity.ok(response));

            boolean result = fraudEngineAdapter.checkFraud(request);

            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Escenario: Configuración del adaptador")
    class AdapterConfiguration {

        @Test
        @DisplayName("Debe implementar la interfaz FraudEngineService")
        mustImplementFraudEngineServiceInterface() {
            assertTrue(fraudEngineAdapter instanceof FraudEngineService);
        }

        @Test
        @DisplayName("Debe usar RestTemplate para las comunicaciones")
        void mustUseRestTemplateForCommunications() {
            CreditRequest request = createTestRequest("client-109", new BigDecimal("5500.00"));
            FraudEngineAdapter.FraudCheckResponse response = 
                    new FraudEngineAdapter.FraudCheckResponse(false, 0.2f);

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenReturn(ResponseEntity.ok(response));

            fraudEngineAdapter.checkFraud(request);

            verify(restTemplate, times(1)).postForEntity(
                    eq(FRAUD_SERVICE_URL),
                    any(),
                    any(Class.class)
            );
        }
    }

    private CreditRequest createTestRequest(String clientId, BigDecimal amount) {
        return new CreditRequest(
                UUID.randomUUID(),
                clientId,
                amount,
                12,
                CreditRequestStatus.PENDING,
                null,
                null
        );
    }
}

// === ARCHIVO: src/test/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapterTest.java ===
package com.pragma.creditflow.infrastructure.adapter;


import com.pragma.creditflow.RiskBureau;
import com.pragma.creditflow.domain.model.CreditRequest;
import com.pragma.creditflow.domain.model.CreditRequest.CreditRequestStatus;
import com.pragma.creditflow.domain.port.RiskBureauService;
import com.pragma.creditflow.infrastructure.exception.ServiceUnavailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RiskBureauAdapterTest - Pruebas unitarias del adaptador del buró de riesgos")
class RiskBureauAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    private RiskBureauAdapter riskBureauAdapter;

    private static final String RISK_BUREAU_URL = "http://risk-bureau-api/internal/evaluate";

    @BeforeEach
    void setUp() {
        riskBureauAdapter = new RiskBureauAdapter(restTemplate);
    }

    @Nested
    @DisplayName("Escenario: Evaluación de riesgo exitosa")
    class RiskEvaluationSuccess {

        @Test
        @DisplayName("Debe retornar true cuando el buró aprueba el riesgo")
        void mustReturnTrueWhenBureauApprovesRisk() {
            CreditRequest request = createTestRequest("client-200", new BigDecimal("5000.00"));
            RiskBureauAdapter.RiskEvaluationResponse response = 
                    new RiskBureauAdapter.RiskEvaluationResponse(true, "approved", new BigDecimal("30000.00"));

            when(restTemplate.postForEntity(
                    eq(RISK_BUREAU_URL),
                    any(RiskBureauAdapter.RiskEvaluationRequest.class),
                    eq(RiskBureauAdapter.RiskEvaluationResponse.class)
            )).thenReturn(ResponseEntity.ok(response));

            boolean result = riskBureauAdapter.evaluateRisk(request);

            assertTrue(result);
            verify(restTemplate).postForEntity(
                    eq(RISK_BUREAU_URL),
                    any(RiskBureauAdapter.RiskEvaluationRequest.class),
                    eq(RiskBureauAdapter.RiskEvaluationResponse.class)
            );
        }

        @Test
        @DisplayName("Debe retornar false cuando el buró rechaza el riesgo")
        void mustReturnFalseWhenBureauRejectsRisk() {
            CreditRequest request = createTestRequest("client-201", new BigDecimal("25000.00"));
            RiskBureauAdapter.RiskEvaluationResponse response = 
                    new RiskBureauAdapter.RiskEvaluationResponse(false, "exceeded_limit", new BigDecimal("10000.00"));

            when(restTemplate.postForEntity(
                    eq(RISK_BUREAU_URL),
                    any(RiskBureauAdapter.RiskEvaluationRequest.class),
                    eq(RiskBureauAdapter.RiskEvaluationResponse.class)
            )).thenReturn(ResponseEntity.ok(response));

            boolean result = riskBureauAdapter.evaluateRisk(request);

            assertFalse(result);
        }

        @Test
        @DisplayName("Debe enviar el clientId correcto al buró de riesgos")
        void mustSendCorrectClientIdToRiskBureau() {
            String expectedClientId = "client-202-test";
            CreditRequest request = createTestRequest(expectedClientId, new BigDecimal("8000.00"));
            RiskBureauAdapter.RiskEvaluationResponse response = 
                    new RiskBureauAdapter.RiskEvaluationResponse(true, "approved", new BigDecimal("50000.00"));

            when(restTemplate.postForEntity(
                    eq(RISK_BUREAU_URL),
                    any(RiskBureauAdapter.RiskEvaluationRequest.class),
                    eq(RiskBureauAdapter.RiskEvaluationResponse.class)
            )).thenReturn(ResponseEntity.ok(response));

            riskBureauAdapter.evaluateRisk(request);

            verify(restTemplate).postForEntity(
                    eq(RISK_BUREAU_URL),
                    argThat(req -> req instanceof RiskBureauAdapter.RiskEvaluationRequest &&
                            ((RiskBureauAdapter.RiskEvaluationRequest) req).clientId().equals(expectedClientId)),
                    eq(RiskBureauAdapter.RiskEvaluationResponse.class)
            );
        }

        @Test
        @DisplayName("Debe enviar el monto y plazo correctos al buró de riesgos")
        void mustSendCorrectAmountAndTermToRiskBureau() {
            BigDecimal expectedAmount = new BigDecimal("10000.00");
            int expectedTerm = 24;
            CreditRequest request = createTestRequest("client-203", expectedAmount, expectedTerm);
            RiskBureauAdapter.RiskEvaluationResponse response = 
                    new RiskBureauAdapter.RiskEvaluationResponse(true, "approved", new BigDecimal("40000.00"));

            when(restTemplate.postForEntity(
                    eq(RISK_BUREAU_URL),
                    any(RiskBureauAdapter.RiskEvaluationRequest.class),
                    eq(RiskBureauAdapter.RiskEvaluationResponse.class)
            )).thenReturn(ResponseEntity.ok(response));

            riskBureauAdapter.evaluateRisk(request);

            verify(restTemplate).postForEntity(
                    eq(RISK_BUREAU_URL),
                    argThat(req -> req instanceof RiskBureauAdapter.RiskEvaluationRequest &&
                            ((RiskBureauAdapter.RiskEvaluationRequest) req).amount()
                                    .compareTo(expectedAmount) == 0 &&
                            ((RiskBureauAdapter.RiskEvaluationRequest) req).termMonths() == expectedTerm),
                    eq(RiskBureauAdapter.RiskEvaluationResponse.class)
            );
        }

        @Test
        @DisplayName("Debe considerar el monto total solicitado en la evaluación")
        void mustConsiderTotalRequestedAmountInEvaluation() {
            BigDecimal amount = new BigDecimal("15000.00");
            int term = 36;
            CreditRequest request = createTestRequest("client-204", amount, term);
            RiskBureauAdapter.RiskEvaluationResponse response = 
                    new RiskBureauAdapter.RiskEvaluationResponse(true, "approved", new BigDecimal("60000.00"));

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenReturn(ResponseEntity.ok(response));

            riskBureauAdapter.evaluateRisk(request);

            verify(restTemplate).postForEntity(
                    eq(RISK_BUREAU_URL),
                    argThat(req -> req instanceof RiskBureauAdapter.RiskEvaluationRequest),
                    eq(RiskBureauAdapter.RiskEvaluationResponse.class)
            );
        }
    }

    @Nested
    @DisplayName("Escenario: Fallo del servicio externo")
    class ExternalServiceFailure {

        @Test
        @DisplayName("Debe lanzar ServiceUnavailableException cuando el servicio no responde")
        void mustThrowServiceUnavailableExceptionWhenServiceDoesNotRespond() {
            CreditRequest request = createTestRequest("client-205", new BigDecimal("5000.00"));

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenThrow(new RestClientException("Connection refused"));

            ServiceUnavailableException exception = assertThrows(
                    ServiceUnavailableException.class,
                    () -> riskBureauAdapter.evaluateRisk(request)
            );

            assertTrue(exception.getMessage().contains("risk") ||
                       exception.getMessage().contains("RiskBureau"));
        }

        @Test
        @DisplayName("Debe lanzar ServiceUnavailableException cuando el servicio retorna error HTTP 500")
        void mustThrowServiceUnavailableExceptionWhenServiceReturnsHttp500() {
            CreditRequest request = createTestRequest("client-206", new BigDecimal("7000.00"));

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

            ServiceUnavailableException exception = assertThrows(
                    ServiceUnavailableException.class,
                    () -> riskBureauAdapter.evaluateRisk(request)
            );

            assertEquals("SERVICE_UNAVAILABLE", exception.getCode());
        }

        @Test
        @DisplayName("Debe lanzar ServiceUnavailableException cuando hay timeout")
        void mustThrowServiceUnavailableExceptionOnTimeout() {
            CreditRequest request = createTestRequest("client-207", new BigDecimal("9000.00"));

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenThrow(new RestClientException("Connect timeout"));

            ServiceUnavailableException exception = assertThrows(
                    ServiceUnavailableException.class,
                    () -> riskBureauAdapter.evaluateRisk(request)
            );

            assertTrue(exception.getMessage().toLowerCase().contains("timeout") ||
                       exception.getMessage().toLowerCase().contains("service"));
        }

        @Test
        @DisplayName("Debe lanzar ServiceUnavailableException cuando el servicio retorna error HTTP 503")
        void mustThrowServiceUnavailableExceptionWhenServiceReturnsHttp503() {
            CreditRequest request = createTestRequest("client-208", new BigDecimal("11000.00"));

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenReturn(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());

            ServiceUnavailableException exception = assertThrows(
                    ServiceUnavailableException.class,
                    () -> riskBureauAdapter.evaluateRisk(request)
            );

            assertEquals("SERVICE_UNAVAILABLE", exception.getCode());
        }
    }

    @Nested
    @DisplayName("Escenario: Manejo de respuestas nulas o inesperadas")
    class NullResponseHandling {

        @Test
        @DisplayName("Debe retornar false cuando la respuesta es null")
        void mustReturnFalseWhenResponseIsNull() {
            CreditRequest request = createTestRequest("client-209", new BigDecimal("4000.00"));

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenReturn(ResponseEntity.ok(null));

            boolean result = riskBureauAdapter.evaluateRisk(request);

            assertFalse(result);
        }

        @Test
        @DisplayName("Debe manejar respuesta con approved null")
        void mustHandleResponseWithNullApproved() {
            CreditRequest request = createTestRequest("client-210", new BigDecimal("6000.00"));
            RiskBureauAdapter.RiskEvaluationResponse response = 
                    new RiskBureauAdapter.RiskEvaluationResponse(null, "pending", new BigDecimal("20000.00"));

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenReturn(ResponseEntity.ok(response));

            boolean result = riskBureauAdapter.evaluateRisk(request);

            assertFalse(result);
        }

        @Test
        @DisplayName("Debe manejar respuesta sin límite de crédito")
        void mustHandleResponseWithoutCreditLimit() {
            CreditRequest request = createTestRequest("client-211", new BigDecimal("5000.00"));
            RiskBureauAdapter.RiskEvaluationResponse response = 
                    new RiskBureauAdapter.RiskEvaluationResponse(true, "approved", null);

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenReturn(ResponseEntity.ok(response));

            boolean result = riskBureauAdapter.evaluateRisk(request);

            assertTrue(result);
        }
    }

    @Nested
    @DisplayName("Escenario: Configuración del adaptador")
    class AdapterConfiguration {

        @Test
        @DisplayName("Debe implementar la interfaz RiskBureauService")
        void mustImplementRiskBureauServiceInterface() {
            assertTrue(riskBureauAdapter instanceof RiskBureauService);
        }

        @Test
        @DisplayName("Debe usar RestTemplate para las comunicaciones")
        void mustUseRestTemplateForCommunications() {
            CreditRequest request = createTestRequest("client-212", new BigDecimal("5500.00"));
            RiskBureauAdapter.RiskEvaluationResponse response = 
                    new RiskBureauAdapter.RiskEvaluationResponse(true, "approved", new BigDecimal("30000.00"));

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenReturn(ResponseEntity.ok(response));

            riskBureauAdapter.evaluateRisk(request);

            verify(restTemplate, times(1)).postForEntity(
                    eq(RISK_BUREAU_URL),
                    any(),
                    any(Class.class)
            );
        }

        @Test
        @DisplayName("Debe estar correctamente inicializado con la URL del servicio")
        void mustBeCorrectlyInitializedWithServiceUrl() {
            CreditRequest request = createTestRequest("client-213", new BigDecimal("8000.00"));
            RiskBureauAdapter.RiskEvaluationResponse response = 
                    new RiskBureauAdapter.RiskEvaluationResponse(false, "exceeded", new BigDecimal("5000.00"));

            when(restTemplate.postForEntity(
                    anyString(),
                    any(),
                    any(Class.class)
            )).thenReturn(ResponseEntity.ok(response));

            riskBureauAdapter.evaluateRisk(request);

            verify(restTemplate).postForEntity(
                    eq(RISK_BUREAU_URL),
                    any(),
                    any(Class.class)
            );
        }
    }

    private CreditRequest createTestRequest(String clientId, BigDecimal amount) {
        return createTestRequest(clientId, amount, 12);
    }

    private CreditRequest createTestRequest(String clientId, BigDecimal amount, int termMonths) {
        return new CreditRequest(
                UUID.randomUUID(),
                clientId,
                amount,
                termMonths,
                CreditRequestStatus.PENDING,
                null,
                null
        );
    }
}
```
