package com.pragma.creditflow.infrastructure.exception;


import com.pragma.creditflow.CircuitBreaker;
public class ServiceUnavailableException extends RuntimeException {
    private final String serviceName;
    private final int retryCount;
    private final long retryDelayMs;
    private final boolean circuitBreakerOpen;
    
    public ServiceUnavailableException(String serviceName) {
        super(buildMessage(serviceName, 0, 0, false));
        this.serviceName = serviceName;
        this.retryCount = 0;
        this.retryDelayMs = 0;
        this.circuitBreakerOpen = false;
    }
    
    public ServiceUnavailableException(String serviceName, int retryCount) {
        super(buildMessage(serviceName, retryCount, 0, false));
        this.serviceName = serviceName;
        this.retryCount = retryCount;
        this.retryDelayMs = 0;
        this.circuitBreakerOpen = false;
    }
    
    public ServiceUnavailableException(String serviceName, int retryCount, long retryDelayMs) {
        super(buildMessage(serviceName, retryCount, retryDelayMs, false));
        this.serviceName = serviceName;
        this.retryCount = retryCount;
        this.retryDelayMs = retryDelayMs;
        this.circuitBreakerOpen = false;
    }
    
    public ServiceUnavailableException(String serviceName, int retryCount, long retryDelayMs, boolean circuitBreakerOpen) {
        super(buildMessage(serviceName, retryCount, retryDelayMs, circuitBreakerOpen));
        this.serviceName = serviceName;
        this.retryCount = retryCount;
        this.retryDelayMs = retryDelayMs;
        this.circuitBreakerOpen = circuitBreakerOpen;
    }
    
    public ServiceUnavailableException(String serviceName, Throwable cause) {
        super(buildMessage(serviceName, 0, 0, false), cause);
        this.serviceName = serviceName;
        this.retryCount = 0;
        this.retryDelayMs = 0;
        this.circuitBreakerOpen = false;
    }
    
    public ServiceUnavailableException(String serviceName, int retryCount, Throwable cause) {
        super(buildMessage(serviceName, retryCount, 0, false), cause);
        this.serviceName = serviceName;
        this.retryCount = retryCount;
        this.retryDelayMs = 0;
        this.circuitBreakerOpen = false;
    }
    
    public ServiceUnavailableException(String serviceName, int retryCount, long retryDelayMs, Throwable cause) {
        super(buildMessage(serviceName, retryCount, retryDelayMs, false), cause);
        this.serviceName = serviceName;
        this.retryCount = retryCount;
        this.retryDelayMs = retryDelayMs;
        this.circuitBreakerOpen = false;
    }
    
    public ServiceUnavailableException(String serviceName, int retryCount, long retryDelayMs, boolean circuitBreakerOpen, Throwable cause) {
        super(buildMessage(serviceName, retryCount, retryDelayMs, circuitBreakerOpen), cause);
        this.serviceName = serviceName;
        this.retryCount = retryCount;
        this.retryDelayMs = retryDelayMs;
        this.circuitBreakerOpen = circuitBreakerOpen;
    }
    
    private static String buildMessage(String serviceName, int retryCount, long retryDelayMs, boolean circuitBreakerOpen) {
        StringBuilder message = new StringBuilder();
        message.append("El servicio externo '");
        message.append(serviceName);
        message.append("' no esta disponible");
        
        if (retryCount > 0) {
            message.append(". Reintentos intentados: ");
            message.append(retryCount);
        }
        
        if (retryDelayMs > 0) {
            message.append(". Delay entre reintentos: ");
            message.append(retryDelayMs);
            message.append("ms");
        }
        
        if (circuitBreakerOpen) {
            message.append(". CircuitBreaker ABIERTO - el servicio esta temporalmente no disponible");
        }
        
        return message.toString();
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public int getRetryCount() {
        return retryCount;
    }
    
    public long getRetryDelayMs() {
        return retryDelayMs;
    }
    
    public boolean isCircuitBreakerOpen() {
        return circuitBreakerOpen;
    }
    
    public boolean hasRetries() {
        return retryCount > 0;
    }
    
    public boolean isTimeoutError() {
        return getCause() instanceof java.util.concurrent.TimeoutException;
    }
    
    public boolean isConnectionError() {
        Throwable cause = getCause();
        if (cause == null) {
            return false;
        }
        return cause instanceof java.io.IOException || 
               cause instanceof java.net.UnknownHostException ||
               cause instanceof java.net.ConnectException;
    }
    
    public String getDetailedMessage() {
        StringBuilder details = new StringBuilder();
        details.append("ServiceUnavailableException{\n");
        details.append("  serviceName: ").append(serviceName).append("\n");
        details.append("  retryCount: ").append(retryCount).append("\n");
        details.append("  retryDelayMs: ").append(retryDelayMs).append("\n");
        details.append("  circuitBreakerOpen: ").append(circuitBreakerOpen).append("\n");
        details.append("  message: ").append(getMessage()).append("\n");
        
        if (getCause() != null) {
            details.append("  cause: ").append(getCause().getClass().getName());
            details.append(": ").append(getCause().getMessage()).append("\n");
        }
        
        details.append("}");
        return details.toString();
    }
}