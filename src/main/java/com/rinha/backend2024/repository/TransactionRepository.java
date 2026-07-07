package com.rinha.backend2024.repository;

import com.rinha.backend2024.infra.dto.TransactionDTO;
import com.rinha.backend2024.infra.entities.Transaction;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<@NonNull Transaction, @NonNull Integer> {
    @Query("SELECT new com.rinha.backend2024.infra.dto.TransactionDTO(c, t) " +
        "FROM Transaction t RIGHT JOIN t.client c " +
        "WHERE c.id = :clientId " +
        "ORDER BY t.transactionDate DESC NULLS LAST")
    List<TransactionDTO> findRawTransactions(Integer clientId);
}


