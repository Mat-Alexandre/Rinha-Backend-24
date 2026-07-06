package com.rinha.backend2024.services;

import com.rinha.backend2024.infra.dto.request.TransactionRequest;
import com.rinha.backend2024.infra.entities.Transaction;
import com.rinha.backend2024.repository.ClientsRepository;
import com.rinha.backend2024.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void addTransaction(Integer clientId, TransactionRequest trxRequest) {
        var newTransaction = new Transaction();

        newTransaction.setTransactionId(UUID.randomUUID().toString());
        newTransaction.setTransactionDate(OffsetDateTime.now(ZoneOffset.UTC));
        newTransaction.setTransactionType(trxRequest.transactionType());
        newTransaction.setValue(trxRequest.transactionValue());
        newTransaction.setTransactionDescription(trxRequest.transactionDescription());

        this.transactionRepository.save(newTransaction);
    }

}
