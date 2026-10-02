# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Implementación de un sistema de validación de extremo a extremo en un flujo de crédito**.

| | |
|---|---|
| Tema | E2E inval |
| Nivel | senior-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean con manejo de eventos y patrones de resiliencia |
| Tiempo estimado | 8 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web n/a
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.springframework.boot:spring-boot-starter-actuator n/a
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- io.github.resilience4j:resilience4j-reactor 2.2.0
- org.postgresql:postgresql n/a
- org.springframework.boot:spring-boot-starter-test n/a
- org.mockito:mockito-core n/a
- org.springdoc:springdoc-openapi-starter-webmvc-ui 2.9.1

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Validación inicial de solicitudes de crédito**: Sistema que valida las solicitudes de crédito y asegura la idempotencia.
- **Fase 2 — Integración con motor antifraude y buró de riesgos**: Sistema integrado con motor antifraude y buró de riesgos, capaz de manejar las respuestas y rechazar solicitudes no válidas.
- **Fase 3 — Recuperación de fallos y reintentos**: Sistema robusto con lógica de recuperación de fallos y reintentos.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/main/java/com/pragma/creditflow/infrastructure/config/ResilienceConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Archivos que la arquitectura declara (2 de 19)

La propuesta arquitectonica del reto los lista y no llegaron al repo. Crealos con implementacion real, respetando la capa en la que viven:

- [ ] `src/main/java/com/ragma/creditflow/infrastructure/exception/CustomException.java`
- [ ] `src/main/java/com/ragma/creditflow/infrastructure/exception/ValidationException.java`

### 2. Referencias colgando (74)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/IdempotencyAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/exception/GlobalExceptionHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/creditflow/CreditFlowApplication.java` — `CreditFlowProperties.getCircuitBreaker`
      Se invoca `getCircuitBreaker` sobre `CreditFlowProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/CreditFlowApplication.java` — `CreditFlowProperties.getTimeout`
      Se invoca `getTimeout` sobre `CreditFlowProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/CreditFlowApplication.java` — `CreditFlowProperties.getFraudEngine`
      Se invoca `getFraudEngine` sobre `CreditFlowProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/CreditFlowApplication.java` — `CreditFlowProperties.getRiskBureau`
      Se invoca `getRiskBureau` sobre `CreditFlowProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `CreditRequest.applicantId`
      Se invoca `applicantId` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `CreditRequest.id`
      Se invoca `id` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `CreditRequest.status`
      Se invoca `status` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `CreditRequest.isPresent`
      Se invoca `isPresent` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `CreditRequest.get`
      Se invoca `get` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `RiskBureauService.checkRisk`
      Se invoca `checkRisk` sobre `RiskBureauService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java` — `CreditRequest.requestedAmount`
      Se invoca `requestedAmount` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/IdempotencyAdapter.java` — `IdempotencyEntity.getRequestId`
      Se invoca `getRequestId` sobre `IdempotencyEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/IdempotencyAdapter.java` — `IdempotencyEntity.setIdempotencyKey`
      Se invoca `setIdempotencyKey` sobre `IdempotencyEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/IdempotencyAdapter.java` — `IdempotencyEntity.setRequestId`
      Se invoca `setRequestId` sobre `IdempotencyEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/IdempotencyAdapter.java` — `IdempotencyEntity.setCreatedAt`
      Se invoca `setCreatedAt` sobre `IdempotencyEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.applicantId`
      Se invoca `applicantId` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setId`
      Se invoca `setId` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setCreatedAt`
      Se invoca `setCreatedAt` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setUpdatedAt`
      Se invoca `setUpdatedAt` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getId`
      Se invoca `getId` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.id`
      Se invoca `id` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setStatus`
      Se invoca `setStatus` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.status`
      Se invoca `status` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setApprovedAmount`
      Se invoca `setApprovedAmount` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.approvedAmount`
      Se invoca `approvedAmount` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setRejectionReason`
      Se invoca `setRejectionReason` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.rejectionReason`
      Se invoca `rejectionReason` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setApplicantId`
      Se invoca `setApplicantId` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setRequestedAmount`
      Se invoca `setRequestedAmount` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.requestedAmount`
      Se invoca `requestedAmount` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setCreditPurpose`
      Se invoca `setCreditPurpose` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.creditPurpose`
      Se invoca `creditPurpose` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.setTermMonths`
      Se invoca `setTermMonths` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequest.termMonths`
      Se invoca `termMonths` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getApplicantId`
      Se invoca `getApplicantId` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getRequestedAmount`
      Se invoca `getRequestedAmount` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getCreditPurpose`
      Se invoca `getCreditPurpose` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getTermMonths`
      Se invoca `getTermMonths` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getStatus`
      Se invoca `getStatus` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getApprovedAmount`
      Se invoca `getApprovedAmount` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getRejectionReason`
      Se invoca `getRejectionReason` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getCreatedAt`
      Se invoca `getCreatedAt` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java` — `CreditRequestEntity.getUpdatedAt`
      Se invoca `getUpdatedAt` sobre `CreditRequestEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java` — `CreditRequest.requestId`
      Se invoca `requestId` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java` — `CreditRequest.documentNumber`
      Se invoca `documentNumber` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java` — `CreditRequest.amount`
      Se invoca `amount` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java` — `CreditRequest.termMonths`
      Se invoca `termMonths` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java` — `CreditRequest.createdAt`
      Se invoca `createdAt` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java` — `CreditRequest.documentNumber`
      Se invoca `documentNumber` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java` — `CreditRequest.amount`
      Se invoca `amount` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java` — `CreditRequest.termMonths`
      Se invoca `termMonths` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java` — `CreditRequest.creditType`
      Se invoca `creditType` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java` — `CreditRequest.requestId`
      Se invoca `requestId` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequestUseCase.processRequest`
      Se invoca `processRequest` sobre `CreditRequestUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `ValidationException.getCode`
      Se invoca `getCode` sobre `ValidationException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `ValidationException.getMessage`
      Se invoca `getMessage` sobre `ValidationException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `RiskBureauService.evaluateRisk`
      Se invoca `evaluateRisk` sobre `RiskBureauService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequest.status`
      Se invoca `status` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequest.rejectionReason`
      Se invoca `rejectionReason` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequest.clientId`
      Se invoca `clientId` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequest.amount`
      Se invoca `amount` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequest.termMonths`
      Se invoca `termMonths` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java` — `CreditRequest.approvedAt`
      Se invoca `approvedAt` sobre `CreditRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapterTest.java` — `ServiceUnavailableException.getMessage`
      Se invoca `getMessage` sobre `ServiceUnavailableException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapterTest.java` — `ServiceUnavailableException.getCode`
      Se invoca `getCode` sobre `ServiceUnavailableException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapterTest.java` — `RiskBureauAdapter.evaluateRisk`
      Se invoca `evaluateRisk` sobre `RiskBureauAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapterTest.java` — `ServiceUnavailableException.getMessage`
      Se invoca `getMessage` sobre `ServiceUnavailableException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapterTest.java` — `ServiceUnavailableException.getCode`
      Se invoca `getCode` sobre `ServiceUnavailableException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (22)

- `pom.xml`
- `src/main/java/com/pragma/creditflow/CreditFlowApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/pragma/creditflow/domain/model/CreditRequest.java`
- `src/main/java/com/pragma/creditflow/domain/model/IdempotencyKey.java`
- `src/main/java/com/ragma/creditflow/domain/port/IdempotencyRepository.java`
- `src/main/java/com/pragma/creditflow/domain/port/CreditRequestRepository.java`
- `src/main/java/com/pragma/creditflow/domain/port/FraudEngineService.java`
- `src/main/java/com/pragma/creditflow/domain/port/RiskBureauService.java`
- `src/main/java/com/pragma/creditflow/application/usecase/CreditRequestUseCase.java`
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/IdempotencyAdapter.java`
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/CreditRequestAdapter.java`
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapter.java`
- `src/main/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapter.java`
- `src/main/java/com/pragma/creditflow/infrastructure/config/ResilienceConfig.java`
- `src/main/java/com/pragma/creditflow/infrastructure/exception/CustomException.java`
- `src/main/java/com/pragma/creditflow/infrastructure/exception/ValidationException.java`
- `src/main/java/com/pragma/creditflow/infrastructure/exception/GlobalExceptionHandler.java`
- `src/main/java/com/ragma/creditflow/infrastructure/exception/ServiceUnavailableException.java`
- `src/test/java/com/pragma/creditflow/application/usecase/CreditRequestUseCaseTest.java`
- `src/test/java/com/pragma/creditflow/infrastructure/adapter/FraudEngineAdapterTest.java`
- `src/test/java/com/pragma/creditflow/infrastructure/adapter/RiskBureauAdapterTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/creditflow`
- `src/main/java/com/pragma/creditflow/domain`
- `src/main/java/com/pragma/creditflow/domain/model`
- `src/main/java/com/pragma/creditflow/domain/port`
- `src/main/java/com/pragma/creditflow/application`
- `src/main/java/com/pragma/creditflow/application/usecase`
- `src/main/java/com/pragma/creditflow/infrastructure`
- `src/main/java/com/pragma/creditflow/infrastructure/adapter`
- `src/main/java/com/pragma/creditflow/infrastructure/config`
- `src/main/java/com/pragma/creditflow/infrastructure/exception`
- `src/main/resources`
- `src/test/java/com/pragma/creditflow`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con manejo de eventos y patrones de resiliencia**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Spring, Tecnología Java, Senior L2
- Brecha que el reto ataca: x

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
