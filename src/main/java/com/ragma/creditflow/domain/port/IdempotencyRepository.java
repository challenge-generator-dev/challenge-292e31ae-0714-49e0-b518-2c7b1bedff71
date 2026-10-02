package com.pragma.creditflow.domain.port;

import com.pragma.creditflow.domain.model.IdempotencyKey;
import java.util.Optional;
import java.util.UUID;

public interface IdempotencyRepository {
    /**
     * Verifica si ya existe una solicitud procesada con la clave de idempotencia dada.
     * @param idempotencyKey la clave de idempotencia a verificar
     * @return el ID de la solicitud asociada a la clave si existe, o Optional.empty() si no existe
     */
    Optional<UUID> findProcessedRequestId(IdempotencyKey idempotencyKey);

    /**
     * Guarda la asociación entre una clave de idempotencia y el ID de una solicitud procesada.
     * @param idempotencyKey la clave de idempotencia
     * @param requestId el ID de la solicitud procesada
     */
    void save(IdempotencyKey idempotencyKey, UUID requestId);
}