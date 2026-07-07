package com.rinha.backend2024.services;

import com.rinha.backend2024.infra.dto.ClientDTO;
import com.rinha.backend2024.infra.dto.request.TransactionRequest;
import com.rinha.backend2024.infra.dto.response.ExtractResponse;
import com.rinha.backend2024.infra.dto.response.SimpleTransactionResponse;
import com.rinha.backend2024.infra.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class ClientTransactionService {
    private final TransactionService trxService;
    private final ClientService clientService;

    public ClientTransactionService(TransactionService trxService, ClientService clientService) {
        this.trxService = trxService;
        this.clientService = clientService;
    }

    public SimpleTransactionResponse createTransaction(Integer clientId, TransactionRequest trxRequest) {
        ClientDTO clientData = this.getAsyncClientData(clientId);

        Integer newBalance = getNewBalance(
                trxRequest.transactionType(),
                trxRequest.transactionValue().intValue(),
                clientData.balance(),
                clientData.limit()
        );
        log.info("New balance: {}", newBalance);

        trxService.addTransaction(clientId, trxRequest);
        clientService.updateClientBalance(clientData, clientId, newBalance);

        log.info("Transaction completed successfully");

        return new SimpleTransactionResponse(
                newBalance,
                clientData.limit()
        );
    }

    public ExtractResponse getLastTransactions(Integer clientId) {
        ExtractResponse clientTransaction;

        try {
            var asyncClientTransaction = this.trxService.getClientLastTransactions(clientId);

            // Wait for all tasks to finish
            CompletableFuture.allOf(asyncClientTransaction).join();

            clientTransaction = asyncClientTransaction.get();
        }catch (ExecutionException e) {
            log.error(e.getMessage());
            throw new AsyncProcessingException("Async execution went wrong. " + e.getMessage());
        }catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new AsyncProcessingException("Async processing was interrupted. " + e.getMessage());
        }catch (ClientNotFoundException e) {
            log.error(e.getMessage());
            throw new ClientNotFoundException(e.getMessage());
        }

        if (clientTransaction == null) {
            var message = "Unable to fetch data for client's transaction. Async processing returned null.";
            log.error(message);
            throw new AsyncProcessingException(message);
        }

        return clientTransaction;
    }

    private static Integer getNewBalance(
            char transactionType,
            Integer transactionValue,
            Integer balance,
            Integer limit
    ) {
        Integer newBalance = switch (transactionType) {
            case 'c' -> balance + transactionValue;
            case 'd' -> balance - transactionValue;
            default -> throw new TransactionTypeException("Transaction type not allowed.");
        };

        if (newBalance < -limit) {
            throw new BalanceInconsistencyException("New balance cannot be lower than client's current limit.");
        }
        return newBalance;
    }

    private ClientDTO getAsyncClientData(Integer clientId) {
        ClientDTO clientData;
        try {
            var asyncClientData = this.clientService.getClientBalance(clientId);

            // Wait for all tasks to finish
            CompletableFuture.allOf(asyncClientData).join();

            clientData = asyncClientData.get();
        }catch (ExecutionException e) {
            log.error(e.getMessage());
            throw new AsyncProcessingException("Async execution went wrong. " + e.getMessage());
        }catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new AsyncProcessingException("Async processing was interrupted. " + e.getMessage());
        }catch (ClientNotFoundException e) {
            log.error(e.getMessage());
            throw new ClientNotFoundException(e.getMessage());
        }

        if (clientData == null) {
            var message = "Unable to fetch data for client. Async processing returned null.";
            log.error(message);
            throw new AsyncProcessingException(message);
        }
        return clientData;
    }
}
