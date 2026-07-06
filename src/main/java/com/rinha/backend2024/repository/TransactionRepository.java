package com.rinha.backend2024.repository;

import com.rinha.backend2024.infra.entities.Transaction;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<@NonNull Transaction, @NonNull String> {
    @Query(nativeQuery = true, value = "SELECT * FROM transaction WHERE client_id = :clientId order by transaction.created_at desc limit 10")
    List<Transaction> listAllByClientIdOrderByCreatedAtDesc(Integer clientId);
}
