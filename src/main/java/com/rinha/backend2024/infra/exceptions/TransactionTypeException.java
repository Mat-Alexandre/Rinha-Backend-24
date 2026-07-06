package com.rinha.backend2024.infra.exceptions;

public class TransactionTypeException extends RuntimeException {
    public TransactionTypeException(String message) {
        super(message);
    }
}
