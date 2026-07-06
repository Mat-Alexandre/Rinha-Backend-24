package com.rinha.backend2024.repository;

import com.rinha.backend2024.infra.entities.Client;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<@NonNull Client, @NonNull Integer> {
}
