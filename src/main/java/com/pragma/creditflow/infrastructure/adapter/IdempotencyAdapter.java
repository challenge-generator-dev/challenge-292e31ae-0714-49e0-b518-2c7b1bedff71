package com.pragma.creditflow.infrastructure.adapter;

import com.pragma.creditflow.domain.model.IdempotencyKey;
import com.pragma.creditflow.domain.port.IdempotencyRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class IdempotencyAdapter implements IdempotencyRepository {

    private static final Logger log = LoggerFactory.getLogger(IdempotencyAdapter.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UUID> findProcessedRequestId(IdempotencyKey idempotencyKey) {
        log.debug("Buscando solicitud procesada para clave de idempotencia: {}", idempotencyKey.getKey());

        try {
            IdempotencyEntity entity = entityManager.find(
                    IdempotencyEntity.class,
                    idempotencyKey.getKey()
            );

            if (entity != null) {
                log.debug("Encontrada solicitud procesada: {} para clave: {}",
                        entity.getRequestId(), idempotencyKey.getKey());
                return Optional.of(entity.getRequestId());
            }

            log.debug("No se encontró solicitud para clave de idempotencia: {}", idempotencyKey.getKey());
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error al buscar clave de idempotencia: {}", idempotencyKey.getKey(), e);
            return Optional.empty();
        }
    }

    @Override
    public void save(IdempotencyKey idempotencyKey, UUID requestId) {
        log.info("Guardando clave de idempotencia: {} para solicitud: {}",
                idempotencyKey.getKey(), requestId);

        try {
            IdempotencyEntity entity = new IdempotencyEntity();
            entity.setIdempotencyKey(idempotencyKey.getKey());
            entity.setRequestId(requestId);
            entity.setCreatedAt(java.time.Instant.now());

            entityManager.persist(entity);
            entityManager.flush();

            log.debug("Clave de idempotencia guardada exitosamente: {}", idempotencyKey.getKey());
        } catch (Exception e) {
            log.error("Error al guardar clave de idempotencia: {} para solicitud: {}",
                    idempotencyKey.getKey(), requestId, e);
            throw new RuntimeException("Failed to save idempotency key", e);
        }
    }

    @Entity
    @Table(name = "idempotency_keys")
    public static class IdempotencyEntity {

        @Id
        private String idempotencyKey;

        private UUID requestId;

        private java.time.Instant createdAt;

        public String getIdempotencyKey() {
            return idempotencyKey;
        }

        public void setIdempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
        }

        public UUID getRequestId() {
            return requestId;
        }

        public void setRequestId(UUID requestId) {
            this.requestId = requestId;
        }

        public java.time.Instant getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(java.time.Instant createdAt) {
            this.createdAt = createdAt;
        }
    }
}