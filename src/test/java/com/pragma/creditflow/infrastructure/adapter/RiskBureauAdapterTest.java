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