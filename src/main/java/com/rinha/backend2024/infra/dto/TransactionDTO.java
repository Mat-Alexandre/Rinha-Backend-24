package com.rinha.backend2024.infra.dto;

import com.rinha.backend2024.infra.entities.Client;
import com.rinha.backend2024.infra.entities.Transaction;

public record TransactionDTO(
        Client client,
        Transaction transaction
) {}