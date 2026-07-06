package com.rinha.backend2024.services;

import com.rinha.backend2024.infra.dto.ClientDTO;
import com.rinha.backend2024.infra.entities.Client;
import com.rinha.backend2024.infra.exceptions.ClientNotFoundException;
import com.rinha.backend2024.repository.ClientsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ClientService {
    private final ClientsRepository clientsRepository;

    public ClientService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<ClientDTO> getClientBalance(Integer clientId) {
        var clientData = this.clientsRepository.findById(clientId).orElseThrow(
                () -> {
                    var msg = "Client not found with id: " + clientId;
                    log.error(msg);
                    return new ClientNotFoundException(msg);
                }
        );
        log.info("Client's current data: {}", clientData);
        return CompletableFuture.completedFuture(
                new ClientDTO(
                        clientData.balance,
                        clientData.limit
                )
        );
    }

    @Async("asyncTaskExecutor")
    public void updateClientBalance(ClientDTO clientDTO, Integer clientId, Integer newBalance) {
        var clientData = new Client();

        clientData.setClientId(clientId);
        clientData.setBalance(newBalance);
        clientData.setLimit(clientDTO.limit());

        log.info("Client's updated data: {}", clientData);

        this.clientsRepository.save(clientData);
    }
}
