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