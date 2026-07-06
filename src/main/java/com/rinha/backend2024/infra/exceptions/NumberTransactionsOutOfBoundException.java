package com.rinha.backend2024.infra.exceptions;

public class NumberTransactionsOutOfBoundException extends RuntimeException {
    public NumberTransactionsOutOfBoundException(String message) {
        super(message);
    }
}
