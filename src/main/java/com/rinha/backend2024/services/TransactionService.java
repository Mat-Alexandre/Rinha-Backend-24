package com.rinha.backend2024.services;

import com.rinha.backend2024.infra.dto.SaldoDTO;
import com.rinha.backend2024.infra.dto.request.TransactionRequest;
import com.rinha.backend2024.infra.dto.response.ExtractResponse;
import com.rinha.backend2024.infra.dto.response.TransactionResponse;
import com.rinha.backend2024.infra.entities.Client;
import com.rinha.backend2024.infra.entities.Transaction;
import com.rinha.backend2024.infra.exceptions.ClientNotFoundException;
import com.rinha.backend2024.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Async("asyncTaskExecutor")
    @Transactional
    public void addTransaction(Integer clientId, TransactionRequest trxRequest) {
        var newTransaction = new Transaction();
        var clientRef = new Client();
        clientRef.setClientId(clientId);

        newTransaction.setTransactionDate(OffsetDateTime.now(ZoneOffset.UTC));
        newTransaction.setTransactionType(trxRequest.transactionType());
        newTransaction.setValue(trxRequest.transactionValue());
        newTransaction.setTransactionDescription(trxRequest.transactionDescription());
        newTransaction.setClient(clientRef);

        this.transactionRepository.save(newTransaction);
    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<ExtractResponse> getClientLastTransactions(Integer clientId) {
        var transactions = this.transactionRepository.findRawTransactions(clientId);

        if (transactions.isEmpty()) {
            throw new ClientNotFoundException("Client with id " + clientId + " not found");
        }

        Client client = transactions.getFirst().client();

        if (transactions.getFirst().transaction() == null) {
            return CompletableFuture.completedFuture(new ExtractResponse(
                    new SaldoDTO(client.getBalance(), OffsetDateTime.now(ZoneOffset.UTC), client.getLimit()),
                    Collections.emptyList()
            ));
        }

        var saldoDto = new SaldoDTO(
                client.getBalance(),
                OffsetDateTime.now(ZoneOffset.UTC),
                client.getLimit()
        );

        var lastTransactions = transactions.stream()
                .map(t -> {
                    var trx = t.transaction();

                    return new TransactionResponse(
                            trx.getValue(),
                            trx.getTransactionType(),
                            trx.getTransactionDescription(),
                            trx.getTransactionDate()
                    );
                })
                .toList();

        return CompletableFuture.completedFuture(new ExtractResponse(saldoDto, lastTransactions));
    }

}
