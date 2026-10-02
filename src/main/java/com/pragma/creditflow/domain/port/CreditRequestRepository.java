package com.pragma.creditflow.domain.port;

import com.pragma.creditflow.domain.model.CreditRequest;
import java.util.Optional;

/**
 * Puerto de persistencia para solicitudes de crédito.
 * Define las operaciones de acceso a datos que el dominio necesita.
 * La implementación concreta reside en la capa de infraestructura.
 */
public interface CreditRequestRepository {

    /**
     * Guarda una solicitud de crédito en el repositorio.
     * @param creditRequest la solicitud a persistir
     * @return la solicitud persistida con su identificador
     */
    CreditRequest save(CreditRequest creditRequest);

    /**
     * Busca una solicitud de crédito por su identificador único.
     * @param id el identificador de la solicitud
     * @return un Optional con la solicitud si existe, vacío si no
     */
    Optional<CreditRequest> findById(java.util.UUID id);

    /**
     * Busca una solicitud de crédito por el identificador externo del cliente.
     * @param clientId el identificador del cliente
     * @return un Optional con la solicitud más reciente del cliente si existe
     */
    Optional<CreditRequest> findTopByClientIdOrderByCreatedAtDesc(String clientId);

    /**
     * Verifica si existe una solicitud de crédito para el cliente given.
     * @param clientId el identificador del cliente
     * @return true si existe al menos una solicitud para el cliente
     */
    boolean existsByClientId(String clientId);

    /**
     * Actualiza el estado de una solicitud de crédito existente.
     * @param creditRequest la solicitud con el estado actualizado
     * @return la solicitud actualizada
     */
    CreditRequest update(CreditRequest creditRequest);
}