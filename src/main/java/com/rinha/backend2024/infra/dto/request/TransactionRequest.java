package com.rinha.backend2024.infra.dto.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.rinha.backend2024.infra.exceptions.TransactionTypeException;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Class used as body request for client's endpoint.
 *
 * @param transactionValue       Positive number that represents the transaction value.
 * @param transactionType        Type of transaction. It can be 'c' or 'd'.
 * @param transactionDescription The transaction description. Must have the size of 1 to 10.
 */
public record TransactionRequest(
        @JsonGetter("valor")
        @Min(value = 1, message = "Value for 'valor' must be a positive integer.")
        @Digits(integer = 10, fraction = 0, message = "Value for 'valor' must be a whole integer")
        BigDecimal transactionValue,

        @JsonGetter("tipo")
        char transactionType,

        @JsonGetter("descricao")
        @NotBlank(message = "Description cannot be empty.")
        @Size(min = 1, max = 10, message = "Value for 'descricao' must be between 1 and 10.")
        String transactionDescription
) {

    public TransactionRequest {
        if(transactionType != 'd' && transactionType != 'c') {
            throw new TransactionTypeException("Transaction type not allowed.");
        }
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "value=" + transactionValue +
                ", type=" + transactionType +
                ", desc='" + transactionDescription + '\'' +
                '}';
    }
}
