package com.rinha.backend2024.infra.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.time.OffsetDateTime;

/**
 * Class used as response for /transacoes endpoint.
 *
 * @param transactionValue       Positive number that represents the transaction value.
 * @param transactionType        Type of transaction. It can be TransactionEnum DEBIT or CREDIT.
 * @param transactionDescription The transaction description. Must have the size of 1 to 10.
 * @param transactionDateTime    The timestamp of the transaction.
 */
public record TransactionResponse(
        @JsonGetter("valor")
        Integer transactionValue,
        @JsonGetter("tipo")
        char transactionType,
        @JsonGetter("descricao")
        String transactionDescription,
        @JsonGetter("realizada_em")
        OffsetDateTime transactionDateTime
) {
}
