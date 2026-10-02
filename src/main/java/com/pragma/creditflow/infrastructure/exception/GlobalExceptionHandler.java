package com.pragma.creditflow.infrastructure.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public record ErrorResponse(
        String errorCode,
        String message,
        Instant timestamp,
        UUID requestId,
        Map<String, Object> details
    ) {}

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex, WebRequest request) {
        logger.error("Excepción personalizada procesada: {}, Código: {}, RequestId: {}",
                ex.getMessage(), ex.getErrorCode(), ex.getRequestId());

        Map<String, Object> details = new HashMap<>();
        if (ex.getDetails() != null) {
            details.put("details", ex.getDetails());
        }

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getTimestamp(),
                ex.getRequestId(),
                details.isEmpty() ? null : details
        );

        HttpStatus status = determineHttpStatus(ex.getErrorCode());
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, WebRequest request) {
        logger.warn("Error de validación: {}, Campo: {}, RequestId: {}",
                ex.getMessage(), ex.getFieldName(), ex.getRequestId());

        Map<String, Object> details = new HashMap<>();

        if (ex.getFieldName() != null) {
            details.put("field", ex.getFieldName());
        }
        if (ex.getRejectedValue() != null) {
            details.put("rejectedValue", ex.getRejectedValue());
        }
        if (ex.getValidationRule() != null) {
            details.put("validationRule", ex.getValidationRule());
        }
        if (ex.hasNestedErrors()) {
            List<Map<String, Object>> nestedErrorsList = ex.getNestedErrors().stream()
                    .map(error -> {
                        Map<String, Object> errorMap = new HashMap<>();
                        errorMap.put("field", error.getField());
                        errorMap.put("message", error.getMessage());
                        errorMap.put("rejectedValue", error.getRejectedValue());
                        errorMap.put("code", error.getCode());
                        return errorMap;
                    })
                    .collect(Collectors.toList());
            details.put("errors", nestedErrorsList);
        }

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getTimestamp(),
                ex.getRequestId(),
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, WebRequest request) {

        logger.warn("Validación de argumentos fallida: {} errores", ex.getBindingResult().getErrorCount());

        List<Map<String, Object>> fieldErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    Map<String, Object> errorMap = new HashMap<>();
                    if (error instanceof FieldError fieldError) {
                        errorMap.put("field", fieldError.getField());
                        errorMap.put("rejectedValue", fieldError.getRejectedValue());
                    } else {
                        errorMap.put("field", error.getObjectName());
                    }
                    errorMap.put("message", error.getDefaultMessage());
                    errorMap.put("code", error.getCode());
                    return errorMap;
                })
                .collect(Collectors.toList());

        Map<String, Object> details = new HashMap<>();
        details.put("errors", fieldErrors);

        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_ERROR",
                "Los datos de la solicitud no son válidos",
                Instant.now(),
                UUID.randomUUID(),
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleServiceUnavailableException(
            ServiceUnavailableException ex, WebRequest request) {

        logger.error("Servicio externo no disponible: {}, Servicio: {}, RequestId: {}",
                ex.getMessage(), ex.getServiceName(), ex.getRequestId());

        Map<String, Object> details = new HashMap<>();
        details.put("serviceName", ex.getServiceName());
        if (ex.getRetryAfter() != null) {
            details.put("retryAfter", ex.getRetryAfter());
        }

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getTimestamp(),
                ex.getRequestId(),
                details
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        logger.warn("Argumento ilegal: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "ILLEGAL_ARGUMENT",
                ex.getMessage(),
                Instant.now(),
                UUID.randomUUID(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        logger.error("Excepción no manejada: ", ex);

        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_ERROR",
                "Ha ocurrido un error interno en el sistema",
                Instant.now(),
                UUID.randomUUID(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private HttpStatus determineHttpStatus(String errorCode) {
        if (errorCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return switch (errorCode) {
            case "NOT_FOUND" -> HttpStatus.NOT_FOUND;
            case "VALIDATION_ERROR" -> HttpStatus.BAD_REQUEST;
            case "CONFLICT" -> HttpStatus.CONFLICT;
            case "FORBIDDEN" -> HttpStatus.FORBIDDEN;
            case "UNAUTHORIZED" -> HttpStatus.UNAUTHORIZED;
            case "SERVICE_UNAVAILABLE" -> HttpStatus.SERVICE_UNAVAILABLE;
            case "TIMEOUT" -> HttpStatus.GATEWAY_TIMEOUT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}