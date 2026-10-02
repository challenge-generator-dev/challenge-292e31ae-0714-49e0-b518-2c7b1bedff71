package com.pragma.creditflow.infrastructure.adapter;

import com.pragma.creditflow.domain.model.CreditRequest;
import com.pragma.creditflow.domain.model.CreditRequest.CreditRequestStatus;
import com.pragma.creditflow.domain.port.CreditRequestRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CreditRequestAdapter implements CreditRequestRepository {

    private static final Logger log = LoggerFactory.getLogger(CreditRequestAdapter.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CreditRequest save(CreditRequest request) {
        log.info("Guardando nueva solicitud de crédito para solicitante: {}", request.applicantId());

        try {
            CreditRequestEntity entity = toEntity(request);
            entity.setId(UUID.randomUUID());
            entity.setCreatedAt(Instant.now());
            entity.setUpdatedAt(Instant.now());

            entityManager.persist(entity);
            entityManager.flush();

            log.debug("Solicitud de crédito guardada con ID: {}", entity.getId());
            return toDomain(entity);
        } catch (Exception e) {
            log.error("Error al guardar solicitud de crédito para solicitante: {}", request.applicantId(), e);
            throw new RuntimeException("Failed to save credit request", e);
        }
    }

    @Override
    public CreditRequest update(CreditRequest request) {
        log.info("Actualizando solicitud de crédito con ID: {}", request.id());

        try {
            CreditRequestEntity entity = entityManager.find(CreditRequestEntity.class, request.id());

            if (entity == null) {
                log.error("No se encontró solicitud de crédito con ID: {}", request.id());
                throw new RuntimeException("Credit request not found: " + request.id());
            }

            entity.setStatus(request.status().name());
            entity.setApprovedAmount(request.approvedAmount());
            entity.setRejectionReason(request.rejectionReason());
            entity.setUpdatedAt(Instant.now());

            entityManager.merge(entity);
            entityManager.flush();

            log.debug("Solicitud de crédito actualizada con ID: {}", entity.getId());
            return toDomain(entity);
        } catch (Exception e) {
            log.error("Error al actualizar solicitud de crédito con ID: {}", request.id(), e);
            throw new RuntimeException("Failed to update credit request", e);
        }
    }

    @Override
    public Optional<CreditRequest> findById(UUID requestId) {
        log.debug("Buscando solicitud de crédito con ID: {}", requestId);

        try {
            CreditRequestEntity entity = entityManager.find(CreditRequestEntity.class, requestId);

            if (entity != null) {
                log.debug("Encontrada solicitud de crédito con ID: {}", requestId);
                return Optional.of(toDomain(entity));
            }

            log.debug("No se encontró solicitud de crédito con ID: {}", requestId);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error al buscar solicitud de crédito con ID: {}", requestId, e);
            return Optional.empty();
        }
    }

    private CreditRequestEntity toEntity(CreditRequest request) {
        CreditRequestEntity entity = new CreditRequestEntity();
        entity.setApplicantId(request.applicantId());
        entity.setRequestedAmount(request.requestedAmount());
        entity.setCreditPurpose(request.creditPurpose());
        entity.setTermMonths(request.termMonths());
        entity.setStatus(request.status().name());
        entity.setApprovedAmount(request.approvedAmount());
        entity.setRejectionReason(request.rejectionReason());
        return entity;
    }

    private CreditRequest toDomain(CreditRequestEntity entity) {
        return new CreditRequest(
                entity.getId(),
                entity.getApplicantId(),
                entity.getRequestedAmount(),
                entity.getCreditPurpose(),
                entity.getTermMonths(),
                CreditRequestStatus.valueOf(entity.getStatus()),
                entity.getApprovedAmount(),
                entity.getRejectionReason(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    @Entity
    @Table(name = "credit_requests")
    public static class CreditRequestEntity {

        @Id
        private UUID id;

        private String applicantId;

        private BigDecimal requestedAmount;

        private String creditPurpose;

        private Integer termMonths;

        private String status;

        private BigDecimal approvedAmount;

        private String rejectionReason;

        private Instant createdAt;

        private Instant updatedAt;

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getApplicantId() {
            return applicantId;
        }

        public void setApplicantId(String applicantId) {
            this.applicantId = applicantId;
        }

        public BigDecimal getRequestedAmount() {
            return requestedAmount;
        }

        public void setRequestedAmount(BigDecimal requestedAmount) {
            this.requestedAmount = requestedAmount;
        }

        public String getCreditPurpose() {
            return creditPurpose;
        }

        public void setCreditPurpose(String creditPurpose) {
            this.creditPurpose = creditPurpose;
        }

        public Integer getTermMonths() {
            return termMonths;
        }

        public void setTermMonths(Integer termMonths) {
            this.termMonths = termMonths;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public BigDecimal getApprovedAmount() {
            return approvedAmount;
        }

        public void setApprovedAmount(BigDecimal approvedAmount) {
            this.approvedAmount = approvedAmount;
        }

        public String getRejectionReason() {
            return rejectionReason;
        }

        public void setRejectionReason(String rejectionReason) {
            this.rejectionReason = rejectionReason;
        }

        public Instant getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Instant createdAt) {
            this.createdAt = createdAt;
        }

        public Instant getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}