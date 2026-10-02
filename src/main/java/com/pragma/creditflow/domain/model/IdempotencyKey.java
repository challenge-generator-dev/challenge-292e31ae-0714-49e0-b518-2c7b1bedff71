package com.pragma.creditflow.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

public final class IdempotencyKey {
    @NotBlank(message = "La clave de idempotencia no puede estar vacía")
    @Size(max = 64, message = "La clave de idempotencia no puede exceder 64 caracteres")
    private final String key;

    private IdempotencyKey(String key) {
        this.key = Objects.requireNonNull(key, "La clave de idempotencia no puede ser nula");
        if (key.isBlank()) {
            throw new IllegalArgumentException("La clave de idempotencia no puede estar vacía");
        }
        if (key.length() > 64) {
            throw new IllegalArgumentException("La clave de idempotencia no puede exceder 64 caracteres");
        }
    }

    public static IdempotencyKey of(String key) {
        return new IdempotencyKey(key);
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdempotencyKey that = (IdempotencyKey) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "IdempotencyKey{" +
                "key='" + key + '\'' +
                '}';
    }
}