package com.pragma.creditflow.infrastructure.exception;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ValidationException extends CustomException {
    private final String fieldName;
    private final Object rejectedValue;
    private final String validationRule;
    private final List<ValidationError> nestedErrors;

    public static class ValidationError {
        private final String field;
        private final String message;
        private final Object rejectedValue;
        private final String code;

        public ValidationError(String field, String message, Object rejectedValue, String code) {
            this.field = field;
            this.message = message;
            this.rejectedValue = rejectedValue;
            this.code = code;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }

        public Object getRejectedValue() {
            return rejectedValue;
        }

        public String getCode() {
            return code;
        }
    }

    public ValidationException(String message, String fieldName, Object rejectedValue, String validationRule) {
        super(message, "VALIDATION_ERROR");
        if (fieldName == null || fieldName.isBlank()) {
            throw new IllegalArgumentException("El nombre del campo no puede ser nulo o vacío");
        }
        this.fieldName = fieldName;
        this.rejectedValue = rejectedValue;
        this.validationRule = validationRule;
        this.nestedErrors = new ArrayList<>();
    }

    public ValidationException(String message, String fieldName, Object rejectedValue) {
        this(message, fieldName, rejectedValue, null);
    }

    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR");
        this.fieldName = null;
        this.rejectedValue = null;
        this.validationRule = null;
        this.nestedErrors = new ArrayList<>();
    }

    public ValidationException(String message, List<ValidationError> errors) {
        super(message, "VALIDATION_ERROR");
        this.fieldName = null;
        this.rejectedValue = null;
        this.validationRule = null;
        this.nestedErrors = errors != null ? new ArrayList<>(errors) : new ArrayList<>();
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public String getValidationRule() {
        return validationRule;
    }

    public List<ValidationError> getNestedErrors() {
        return Collections.unmodifiableList(nestedErrors);
    }

    public ValidationException withNestedError(String field, String message, Object rejectedValue, String code) {
        this.nestedErrors.add(new ValidationError(field, message, rejectedValue, code));
        return this;
    }

    public boolean hasNestedErrors() {
        return !nestedErrors.isEmpty();
    }

    public static ValidationException forField(String fieldName, Object rejectedValue, String rule) {
        String message = String.format("Validación fallida para el campo '%s' con valor '%s'", fieldName, rejectedValue);
        return new ValidationException(message, fieldName, rejectedValue, rule);
    }

    public static ValidationException required(String fieldName) {
        String message = String.format("El campo '%s' es obligatorio", fieldName);
        return new ValidationException(message, fieldName, null, "NOT_NULL");
    }

    public static ValidationException invalidFormat(String fieldName, Object rejectedValue) {
        String message = String.format("El campo '%s' tiene un formato inválido: %s", fieldName, rejectedValue);
        return new ValidationException(message, fieldName, rejectedValue, "INVALID_FORMAT");
    }

    @Override
    public String toString() {
        return "ValidationException{" +
                "fieldName='" + fieldName + '\'' +
                ", rejectedValue=" + rejectedValue +
                ", validationRule='" + validationRule + '\'' +
                ", nestedErrorsCount=" + nestedErrors.size() +
                ", errorCode='" + getErrorCode() + '\'' +
                ", requestId=" + getRequestId() +
                '}';
    }
}