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