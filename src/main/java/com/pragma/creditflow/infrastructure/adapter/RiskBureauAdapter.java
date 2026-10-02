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