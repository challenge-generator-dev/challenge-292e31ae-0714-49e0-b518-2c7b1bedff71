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