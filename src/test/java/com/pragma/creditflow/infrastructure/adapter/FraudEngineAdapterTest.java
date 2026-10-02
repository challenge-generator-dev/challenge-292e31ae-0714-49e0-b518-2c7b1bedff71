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