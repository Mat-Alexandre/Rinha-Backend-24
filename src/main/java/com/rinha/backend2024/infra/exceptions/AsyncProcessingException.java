package com.rinha.backend2024.infra.exceptions;

public class AsyncProcessingException extends RuntimeException {
    public AsyncProcessingException(String message) {
        super(message);
    }
}
