package com.rinha.backend2024.infra.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

/**
 * Class used as DTO for client's operations.
 *
 * @param balance The total of money registered in client's account.
 * @param limit   The lower bound that the balance cannot exceed.
 */
public record ClientDTO(
        @JsonGetter("saldo")
        Integer balance,
        @JsonGetter("limite")
        Integer limit
) {
}
