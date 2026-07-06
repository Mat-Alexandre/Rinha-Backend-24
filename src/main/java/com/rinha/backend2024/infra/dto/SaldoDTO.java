package com.rinha.backend2024.infra.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.time.OffsetDateTime;

/**
 * Class used as DTO for client's balance.
 *
 * @param total       The current total money in client's account.
 * @param dateExtract The date time which the operation was executed.
 * @param limit       The current client's limit.
 */
public record SaldoDTO(
        Integer total,
        @JsonGetter("data_extrato")
        OffsetDateTime dateExtract,
        @JsonGetter("limite")
        Integer limit
) {
}
