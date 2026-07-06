package com.rinha.backend2024.infra.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.rinha.backend2024.infra.dto.SaldoDTO;

import java.util.List;

/**
 * Class used as response for /extrato endpoint.
 *
 * @param balance          Information about client's balance.
 * @param lastTransactions List of (maximum of 10) last client's transactions.
 * @see SaldoDTO
 */
public record ExtractResponse(
        @JsonGetter("saldo")
        SaldoDTO balance,
        @JsonGetter("ultimas_transacoes")
        List<TransactionResponse> lastTransactions
) {
}