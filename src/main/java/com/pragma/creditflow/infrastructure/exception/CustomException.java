package com.pragma.creditflow.infrastructure.exception;

import java.time.Instant;
import java.util.UUID;

public class CustomException extends RuntimeException {
    private final String errorCode;
    private final Instant timestamp;
    private final String details;
    private final UUID requestId;

    public CustomException(String message, String errorCode) {
        super(message);
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("El mensaje de error no puede ser nulo o vacío");
        }
        if (errorCode == null || errorCode.isBlank()) {
            throw new IllegalArgumentException("El código de error no puede ser nulo o vacío");
        }
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
        this.details = null;
        this.requestId = UUID.randomUUID();
    }

    public CustomException(String message, String errorCode, String details) {
        this(message, errorCode);
        this.details = details;
    }

    public CustomException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("El mensaje de error no puede ser nulo o vacío");
        }
        if (errorCode == null || errorCode.isBlank()) {
            throw new IllegalArgumentException("El código de error no puede ser nulo o vacío");
        }
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
        this.details = cause != null ? cause.getMessage() : null;
        this.requestId = UUID.randomUUID();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public static CustomException of(String message, String errorCode) {
        return new CustomException(message, errorCode);
    }

    public static CustomException withDetails(String message, String errorCode, String details) {
        return new CustomException(message, errorCode, details);
    }

    public static CustomException withCause(String message, String errorCode, Throwable cause) {
        return new CustomException(message, errorCode, cause);
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "errorCode='" + errorCode + '\'' +
                ", timestamp=" + timestamp +
                ", details='" + details + '\'' +
                ", requestId=" + requestId +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}