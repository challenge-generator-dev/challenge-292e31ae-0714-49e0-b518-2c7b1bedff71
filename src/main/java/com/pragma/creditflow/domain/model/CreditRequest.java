package com.pragma.creditflow.domain.model;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreditRequest(
    @NotNull(message = "El ID de la solicitud no puede ser nulo")
    UUID requestId,

    @NotNull(message = "El ID del cliente no puede ser nulo")
    UUID customerId,

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser positivo")
    BigDecimal amount,

    @NotNull(message = "La fecha de solicitud no puede ser nula")
    LocalDate requestDate,

    @NotNull(message = "El plazo en meses no puede ser nulo")
    @Min(value = 1, message = "El plazo en meses debe ser al menos 1")
    @Max(value = 360, message = "El plazo en meses no puede exceder 360")
    Integer termMonths,

    @NotBlank(message = "El propósito del crédito no puede estar vacío")
    @Size(max = 255, message = "El propósito del crédito no puede exceder 255 caracteres")
    String purpose,

    @NotNull(message = "El estado de la solicitud no puede ser nulo")
    CreditRequestStatus status,

    @NotNull(message = "La clave de idempotencia no puede ser nula")
    IdempotencyKey idempotencyKey
) {
    public enum CreditRequestStatus {
        PENDING,
        APPROVED,
        REJECTED,
        FRAUD_DETECTED,
        RISK_LIMIT_EXCEEDED
    }

    public CreditRequest {
        if (requestId == null) {
            requestId = UUID.randomUUID();
        }
        if (requestDate == null) {
            requestDate = LocalDate.now();
        }
    }

    public CreditRequest withStatus(CreditRequestStatus newStatus) {
        return new CreditRequest(
            this.requestId,
            this.customerId,
            this.amount,
            this.requestDate,
            this.termMonths,
            this.purpose,
            newStatus,
            this.idempotencyKey
        );
    }

    public boolean isApproved() {
        return CreditRequestStatus.APPROVED.equals(this.status);
    }

    public boolean isRejected() {
        return CreditRequestStatus.REJECTED.equals(this.status) ||
               CreditRequestStatus.FRAUD_DETECTED.equals(this.status) ||
               CreditRequestStatus.RISK_LIMIT_EXCEEDED.equals(this.status);
    }
}