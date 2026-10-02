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