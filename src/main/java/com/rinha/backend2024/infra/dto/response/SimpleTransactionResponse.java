package com.rinha.backend2024.infra.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;

public record SimpleTransactionResponse(
        @JsonGetter("saldo")
        Integer balance,
        @JsonGetter("limite")
        Integer limit
) {
}
