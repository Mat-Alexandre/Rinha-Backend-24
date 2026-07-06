package com.rinha.backend2024.infra.exceptions;

public class BalanceInconsistencyException extends RuntimeException {
    public BalanceInconsistencyException(String message) {
        super(message);
    }
}
