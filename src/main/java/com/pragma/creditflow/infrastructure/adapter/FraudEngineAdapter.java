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