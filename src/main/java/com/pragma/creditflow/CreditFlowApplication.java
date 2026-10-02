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