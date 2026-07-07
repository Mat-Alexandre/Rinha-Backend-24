package com.rinha.backend2024.controllers;

import com.rinha.backend2024.infra.dto.request.TransactionRequest;
import com.rinha.backend2024.infra.dto.response.ExtractResponse;
import com.rinha.backend2024.infra.dto.response.SimpleTransactionResponse;
import com.rinha.backend2024.services.ClientTransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/clientes")
public class ClientController {
    private final ClientTransactionService ctService;

    public ClientController(ClientTransactionService ctService) {
        this.ctService = ctService;
    }

    @PostMapping("/{clientId}/transacoes")
    public final ResponseEntity<@NonNull SimpleTransactionResponse> clientTransaction(
            @PathVariable Integer clientId,
            @RequestBody @Valid TransactionRequest body
    ) {
        log.info("Parameters:\n\tClientId: {}\n\tRequest Body: {}\n", clientId, body);
        var response = this.ctService.createTransaction(clientId, body);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{clientId}/extrato")
    public final ResponseEntity<@NonNull ExtractResponse> clientExtract(@PathVariable Integer clientId) {
        log.info("Fetching transactions from client id: {}", clientId);
        var response = this.ctService.getLastTransactions(clientId);
        return ResponseEntity.ok().body(response);
    }
}